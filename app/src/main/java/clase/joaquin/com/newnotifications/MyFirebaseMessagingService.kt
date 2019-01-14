package clase.joaquin.com.newnotifications

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.app.NotificationManager
import android.media.RingtoneManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat
import com.google.firebase.iid.FirebaseInstanceId


class MyFirebaseMessagingService : FirebaseMessagingService() {
    val TAG = "FirebaseMessagingService"

    val TAG2 = "PushNotifService"
    lateinit var name: String

    override fun onNewToken(p0: String?) {
        val token = FirebaseInstanceId.getInstance().token
        Log.d(TAG2, "Token: ${token}")
    }

    @SuppressLint("LongLogTag")
    //Cuando recibo un mensaje creo la notificación
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "Notification: ${remoteMessage.from}")
        // si la notification no es null, creala
        if (remoteMessage.notification != null) {
            //utilizo shownotification con el title y body que he recibido
            showNotification(remoteMessage.notification?.title, remoteMessage.notification?.body)
        }
    }
    //Creo la notificacion y la muestro
    private fun showNotification(title: String?, body: String?) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val mBuilder = NotificationCompat.Builder(this, "1")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
                //le pongo titulo y body
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        //Se notifica para mostrarlo
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, mBuilder.build())
    }


    }
