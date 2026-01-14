package com.example.mi.powiadomienia

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.mi.R

object Notyfikacje {

    const val KANAL_ID = "kanal_zdrapki"
    private const val KANAL_NAZWA = "Zdrapki"
    private const val KANAL_OPIS = "Codzienne przypomnienia o nowej zdrapce"

    fun utworzKanalJesliTrzeba(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val kanal = NotificationChannel(
                KANAL_ID,
                KANAL_NAZWA,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = KANAL_OPIS
            }
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(kanal)
        }
    }

    fun pokazPowiadomienieZdrapki(context: Context) {
        utworzKanalJesliTrzeba(context)

        val notif = NotificationCompat.Builder(context, KANAL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Nowa zdrapka 🎟️")
            .setContentText("Wejdź i zdrap dzisiejszą zdrapkę!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context).notify(1001, notif)
    }
}
