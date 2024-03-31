package com.hegunhee.sample_playground.broadcast

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.hegunhee.sample_playground.R
import com.hegunhee.sample_playground.broadcast.AlarmReceiver.Companion.ALARM_REPEAT
import com.hegunhee.sample_playground.broadcast.AlarmReceiver.Companion.ALARM_SECOND
import com.hegunhee.sample_playground.feature.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    private lateinit var notificationManager : NotificationManager

    override fun onReceive(context: Context, intent: Intent) {
        notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createAlarmNotificationChannel()
        val strExtra = intent.getStringExtra(ALARM_EXTRA_KEY) ?: ""
        when(strExtra) {
            ALARM_SECOND -> {
                sendNotification(context,"몇초후 알람입니다.","내용 입니다.")
            }
            ALARM_REPEAT -> {
                sendNotification(context,"오늘의 반복 알람입니다.","반복반복")
            }
        }
    }

    private fun createAlarmNotificationChannel() {
        NotificationChannel(
            CHANNEL_ID,
            "채널 이름",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "채널의 정보입니다."
            notificationManager.createNotificationChannel(this)
        }
    }

    private fun sendNotification(context : Context,title : String, content : String) {
        val contentIntent = Intent(context,MainActivity::class.java)
        val contentPendingIntent = PendingIntent.getActivity(
            context,
            NOTIFICATION_ID,
            contentIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val builder =NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_check_circle_24)
            .setContentTitle(title)
            .setContentText(content)
            .setContentIntent(contentPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
        notificationManager.notify(NOTIFICATION_ID,builder.build())
    }

    companion object {
        const val SECOND_ALARM_PENDING_INTENT_FLAG = 1

        const val REPEAT_ALARM_PENDING_INTENT_FLAG = 2

        private const val ALARM_EXTRA_KEY = "ALARM"

        const val ALARM_SECOND = "SECOND"

        const val ALARM_REPEAT = "REPEAT"

        private const val CHANNEL_ID = "테스트 알람 채널 id"

        private const val NOTIFICATION_ID = 1


        private fun getAlarmReceiverIntent(context : Context,alarmType : AlarmType) : Intent {
            return Intent(context,AlarmReceiver::class.java).apply {
                putExtra(ALARM_EXTRA_KEY,alarmType.name)
            }
        }

        fun getAlarmPendingIntent(context : Context, alarmType : AlarmType,pendingIntentPlag : Int) : PendingIntent {
            return getAlarmReceiverIntent(context,alarmType).let { intent ->
                PendingIntent.getBroadcast(context, pendingIntentPlag,intent,PendingIntent.FLAG_IMMUTABLE)
            }
        }
    }
}

enum class AlarmType(name : String) {
    SECOND(name = ALARM_SECOND), REPEAT(name = ALARM_REPEAT)
}