package com.example.hwandroidapplication2part1

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import androidx.core.app.RemoteInput
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.app.NotificationCompat
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private lateinit var notificationManager: NotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotificationChannel()

        setBasicNotification()
        setActionNotification()
        setReplyNotification()
        setProgressNotification()

        createReceiver(this)
    }

    companion object {
        const val REPLY_STRING_KEY = "REPLY_KEY"
        const val REPLY_INTENT_ACTION = "ACTION_REPLY"
        const val channelId = "1"
        const val basicNotificationId = 1
        const val actionNotificationId = 2
        const val replyNotificationId = 3
        const val progressNotificationId = 4
    }

    private fun setBasicNotification() {
        findViewById<Button>(R.id.basic_notification).setOnClickListener {
            val builder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Basic notification")
                .setContentText("Some text")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            notificationManager.notify(basicNotificationId, builder.build())
        }
    }

    private fun setActionNotification() {
        findViewById<Button>(R.id.action_notification).setOnClickListener {
            val link =
                "https://developer.android.com/training/notify-user/build-notification#Actions"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

            val builder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Action notification")
                .setContentText("Press button to open the link")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(R.drawable.ic_launcher_background, "Open link", pendingIntent)

            notificationManager.notify(actionNotificationId, builder.build())
        }
    }

    private fun setReplyNotification() {
        findViewById<Button>(R.id.reply_notification).setOnClickListener {
            val label = "Reply"
            val remoteInput = RemoteInput.Builder(REPLY_STRING_KEY).run {
                setLabel(label)
                build()
            }
            val intent = Intent(REPLY_INTENT_ACTION)
            val pendingIntent =
                PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            val action = NotificationCompat.Action.Builder(
                R.drawable.ic_launcher_background,
                label, pendingIntent
            )
                .addRemoteInput(remoteInput)
                .build()

            val builder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Reply notification")
                .setContentText("Reply to the notification")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(action)

            notificationManager.notify(replyNotificationId, builder.build())
        }
    }

    private fun setProgressNotification() {
        findViewById<Button>(R.id.progress_notification).setOnClickListener {
            val builder = NotificationCompat.Builder(this, channelId)
                .setContentTitle("Wasting time")
                .setContentText("In progress")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationCompat.PRIORITY_LOW)

            val PROGRESS_MAX = 100
            var PROGRESS_CURRENT = 0

            builder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false)
            notificationManager.notify(progressNotificationId, builder.build())

            Executors.newSingleThreadExecutor().execute {
                for (i in 0..4) {
                    Thread.sleep(1500)
                    PROGRESS_CURRENT += 20
                    builder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false)
                    notificationManager.notify(progressNotificationId, builder.build())
                }
                builder.setContentText("Done!")
                    .setProgress(0, 0, false)
                notificationManager.notify(progressNotificationId, builder.build())
            }
        }
    }


    private fun createReceiver(context: Context) {
        context.registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val message = RemoteInput.getResultsFromIntent(intent)?.getCharSequence(
                    REPLY_STRING_KEY
                ).toString()

                (context as MainActivity?)?.findViewById<TextView>(R.id.reply_text)?.text = message

                val builder = NotificationCompat.Builder(context as MainActivity, channelId)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Message sent")

                notificationManager.notify(replyNotificationId, builder.build())
            }

        }, IntentFilter(REPLY_INTENT_ACTION))
    }

    private fun createNotificationChannel() {
        notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "TestChannel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance)

            notificationManager.createNotificationChannel(channel)
        }
    }

}