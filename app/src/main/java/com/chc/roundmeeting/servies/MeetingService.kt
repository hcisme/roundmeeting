package com.chc.roundmeeting.servies

import android.app.Notification
import android.app.Service
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.chc.roundmeeting.R

class MeetingService : Service() {
    companion object {
        const val CONTENT_TITLE = "会议进行中"
        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "1"
    }

    override fun onBind(intent: Intent?) = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(NOTIFICATION_ID, createNotification())
        return START_STICKY
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(CONTENT_TITLE)
            .setSmallIcon(R.drawable.meeting)
            .build()
    }
}
