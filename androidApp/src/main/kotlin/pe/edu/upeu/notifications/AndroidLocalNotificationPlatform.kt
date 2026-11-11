package pe.edu.upeu.notifications

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import pe.edu.upeu.MainActivity
import pe.edu.upeu.data.notification.LocalNotificationPlatform

class AndroidLocalNotificationPlatform(
    private val context : Context
): LocalNotificationPlatform {
    override suspend fun showTestNotification(): Result<Unit> = runCatching{
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && ContextCompat.checkSelfPermission(
            context,
                Manifest.permission.POST_NOTIFICATIONS
        )!= PackageManager.PERMISSION_GRANTED
            ){
            throw SecurityException("Concede el permiso de las notificaciones");
        }
        val notificationManager = NotificationManagerCompat.from(context)
        if (!notificationManager.areNotificationsEnabled()){
            throw SecurityException("Las notificaciones estan bloquedas en los ajustes de android")
        }
        createNotificationChannel()
        val openAppIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            TEST_NOTIFICATION_ID,
            openAppIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(context,CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Booking App")
            .setContentText("¡Las notificaciones locales estan funcionando")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()
        notificationManager.notify(TEST_NOTIFICATION_ID, notification)

    }

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Notificaciones de prueba",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Canal para verficar las notificaciones mode local"
            }
            context.getSystemService(NotificationManager::class.java)
                .createNotificationChannel(channel)
        }
    }


    private companion object{
        const val CHANNEL_ID = "booking_local_notifications"
        const val TEST_NOTIFICATION_ID = 1001
    }
}