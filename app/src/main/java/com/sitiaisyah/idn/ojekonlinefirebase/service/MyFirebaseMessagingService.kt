package com.sitiaisyah.idn.ojekonlinefirebase.service

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.sitiaisyah.idn.ojekonlinefirebase.MainActivity
import com.sitiaisyah.idn.ojekonlinefirebase.R

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage) {

        showNotification()
    }

    @SuppressLint("WrongConstant")
    private fun showNotification() {
        val notificationBuilder = NotificationCompat.Builder(this)
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pageIntent = PendingIntent.getActivity(this, 0, intent,
            Intent.FLAG_ACTIVITY_NEW_TASK)

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        notificationBuilder.setSmallIcon(R.drawable.icon)
            .setBadgeIconType(R.drawable.icon)
            .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.icon))
            .setContentIntent(pageIntent)
            .setContentTitle("New Order, yuk ambil")
            .setContentText("Silakhkan Check list")
            .setAutoCancel(true)
            .setSound(defaultSoundUri)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager

        notificationManager.notify(0, notificationBuilder.build())

    }
}