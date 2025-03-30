package com.chc.roundmeeting.services.floatwindow

import android.animation.ValueAnimator
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.graphics.Point
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.ui.platform.ComposeView
import androidx.core.animation.doOnCancel
import androidx.core.animation.doOnEnd
import androidx.core.app.NotificationCompat
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.chc.roundmeeting.R

class FloatingWindowService : LifecycleService(), SavedStateRegistryOwner, ViewModelStoreOwner {
    companion object {
        const val START_FOREGROUND_ID = 1
        const val INITIAL_X = 0
        const val INITIAL_Y = 400
        const val DEFAULT_CHANNEL_ID = "default_channel_id_$START_FOREGROUND_ID"
        const val CHANNEL_ID = "channelId"
        const val CONTENT_TITLE = "会议运行中"
        const val CHANNEL_NAME = "会议程序"
        var intent: Intent? = null

        fun start(context: Context) {
            intent = Intent(context, FloatingWindowService::class.java)
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(intent)
                } else {
                    context.startService(intent)
                }
            } catch (e: SecurityException) {
                Toast.makeText(context, "启动服务失败", Toast.LENGTH_SHORT).show()
                Log.e("@@ Service", "启动服务失败: ${e.message}")
            }
        }

        fun stop(context: Context) {
            context.stopService(intent)
            intent = null
        }
    }

    private val serviceViewModelStore: ViewModelStore by lazy { ViewModelStore() }
    override val viewModelStore: ViewModelStore
        get() = serviceViewModelStore
    private val savedStateRegistryController = SavedStateRegistryController.create(this)
    private lateinit var windowManager: WindowManager
    private lateinit var composeView: ComposeView
    override val savedStateRegistry: SavedStateRegistry
        get() = savedStateRegistryController.savedStateRegistry
    private val screenWidth: Int by lazy {
        val windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            windowManager.currentWindowMetrics.bounds.width()
        } else {
            val size = Point()
            windowManager.defaultDisplay.getSize(size)
            size.x
        }
    }
    private var currentAnimator: ValueAnimator? = null

    override fun onCreate() {
        super.onCreate()
        savedStateRegistryController.performAttach()
        savedStateRegistryController.performRestore(null)

        startForeground(START_FOREGROUND_ID, createNotification())
        setupFloatingWindow()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        val params = createWindowParams()
        windowManager.addView(composeView, params)
        return super.onStartCommand(intent, flags, startId)
    }

    private fun setupFloatingWindow() {
        composeView = ComposeView(this).apply {
            setViewTreeLifecycleOwner(this@FloatingWindowService)
            setViewTreeSavedStateRegistryOwner(this@FloatingWindowService)

            setContent {
                FloatingWindowContent(
                    service = this@FloatingWindowService,
                    onDrag = { dx, dy ->
                        updateWindowPosition(
                            composeView.x.toInt() + dx.toInt(),
                            composeView.y.toInt() + dy.toInt()
                        )
                    },
                    onDragEnd = {
                        val view = composeView
                        val observer = view.viewTreeObserver
                        val listener = object : ViewTreeObserver.OnPreDrawListener {
                            override fun onPreDraw(): Boolean {
                                // 1. 立即移除监听器避免重复触发
                                observer.removeOnPreDrawListener(this)

                                // 2. 执行吸附逻辑
                                val viewWidth = view.width
                                val currentX = (view.layoutParams as WindowManager.LayoutParams).x
                                val shouldStickLeft = (currentX + viewWidth / 2) < screenWidth / 2
                                val targetX = if (shouldStickLeft) 0 else screenWidth - viewWidth
                                animateToEdge(targetX)

                                // 3. 返回 true 允许本次绘制继续
                                return true
                            }
                        }
                        observer.addOnPreDrawListener(listener)
                    }
                )
            }
        }
    }

    private fun updateWindowPosition(dx: Int, dy: Int) {
        val params = composeView.layoutParams as WindowManager.LayoutParams
        params.x += dx
        params.y += dy
        windowManager.updateViewLayout(composeView, params)
    }

    private fun createWindowParams() = WindowManager.LayoutParams(
        WindowManager.LayoutParams.WRAP_CONTENT,
        WindowManager.LayoutParams.WRAP_CONTENT,
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            @Suppress("DEPRECATION")
            WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
        },
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
        PixelFormat.TRANSLUCENT
    ).apply {
        gravity = Gravity.START or Gravity.TOP
        x = INITIAL_X
        y = INITIAL_Y
    }

    private fun animateToEdge(targetX: Int) {
        // 防止动画执行过程中再次拖动
        currentAnimator?.cancel() // 终止之前的动画

        val params = composeView.layoutParams as? WindowManager.LayoutParams ?: return
        if (!isViewAttached()) return

        currentAnimator = ValueAnimator.ofInt(params.x, targetX).apply {
            duration = 400
            interpolator = DecelerateInterpolator()
            addUpdateListener { animation ->
                // 添加有效性检查
                if (isViewAttached()) {
                    composeView.layoutParams = params.apply {
                        x = animation.animatedValue as Int
                    }
                    try {
                        windowManager.updateViewLayout(composeView, composeView.layoutParams)
                    } catch (_: Exception) {
                        cancel()
                    }
                } else {
                    cancel()
                }
            }
            doOnCancel { currentAnimator = null }
            doOnEnd { currentAnimator = null }
        }.also { it.start() }
    }

    private fun createNotification(): Notification {
        val channelId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        } else DEFAULT_CHANNEL_ID

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle(CONTENT_TITLE)
            .setSmallIcon(R.drawable.float_window)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }

    /**
     * 从 Android 8.0 开始，系统要求所有通知必须关联一个通知渠道
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(): String {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW
        )
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        return CHANNEL_ID
    }

    /**
     * 检查视图是否仍然附加到窗口
     */
    private fun isViewAttached(): Boolean = composeView.parent != null

    override fun onDestroy() {
        currentAnimator?.let {
            it.removeAllUpdateListeners()
            it.cancel()
        }
        currentAnimator = null

        composeView.let {
            // 先禁用视图更新
            it.isVisible = false

            // 强制立即执行所有挂起的UI操作
            it.post {
                windowManager.removeView(it)
                it.disposeComposition()
            }
        }
        super.onDestroy()
    }
}
