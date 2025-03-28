package com.chc.roundmeeting.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.chc.roundmeeting.R

class MeetingService : Service() {
    companion object {
        const val CONTENT_TITLE = "会议进行中"
        const val CHANNEL_NAME = "会议通知"
        const val DEFAULT_CHANNEL_ID = "default_channel_id_1"
        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "1"
        private var intent: Intent? = null

        fun start(context: Context) {
            intent = Intent(context, MeetingService::class.java)
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

    override fun onBind(intent: Intent?) = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(NOTIFICATION_ID, createNotification())
        return START_STICKY
    }

    private fun createNotification(): Notification {
        val channelId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        } else DEFAULT_CHANNEL_ID

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle(CONTENT_TITLE)
            .setSmallIcon(R.drawable.meeting)
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
}
