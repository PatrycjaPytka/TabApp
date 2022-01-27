package com.example.tabapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val buider = NotificationCompat.Builder(this, "notifyMedicine")
            .setSmallIcon(R.drawable.ic_baseline_add_24)
            .setContentTitle("Przypomnienie o przyjęciu leków!")
            .setContentText("Uwaga! Za 10 minut zostanie uruchomiony alarm informujący o potrzebie przyjęcia leku.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(200, buider.build())
    }
}