package com.damars.damarbuildsubmissionke3.reminder

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.damars.damarbuildsubmissionke3.MainActivity
import com.damars.damarbuildsubmissionke3.R
import java.util.*

class RemindOnsAlarm : BroadcastReceiver() {
    override fun onReceive(conTxt: Context, movInt: Intent) {
        val envelop = movInt.getStringExtra(onEnvelop)
        notifyShowingOnAlm(conTxt,envelop)
    }

    fun shutDownAlm(conTxt: Context){
        val almManage = conTxt.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val movInt = Intent(conTxt, RemindOnsAlarm::class.java)
        val onCodeReq = Id_Day
        val onIntPending = PendingIntent.getBroadcast(conTxt, onCodeReq, movInt, 0)
        onIntPending.cancel()
        almManage.cancel(onIntPending)
        Toast.makeText(conTxt,"Daily Reminder Is OFF", Toast.LENGTH_SHORT).show()
    }

    fun setAlmRemind(setConTxt: Context, itsType: String, itsMsg: String) {
        val almManage = setConTxt.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val movSTime = Intent(setConTxt, RemindOnsAlarm::class.java)
        movSTime.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        movSTime.putExtra(onEnvelop, itsMsg)
        movSTime.putExtra(itType, itsType)
        val regexTime = onTimeToday.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val settingCalAlm = Calendar.getInstance()
        settingCalAlm.set(Calendar.HOUR_OF_DAY,Integer.parseInt(regexTime[0]))
        settingCalAlm.set(Calendar.MINUTE, Integer.parseInt(regexTime[1]))
        settingCalAlm.set(Calendar.SECOND, 0)
        val onIntPending = PendingIntent.getBroadcast(
            setConTxt,
            Id_Day, movSTime, PendingIntent.FLAG_ONE_SHOT
        )
        almManage.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            settingCalAlm.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            onIntPending
        )
        Toast.makeText(setConTxt, "Daily Reminder Is On", Toast.LENGTH_SHORT).show()
    }
    private fun notifyShowingOnAlm(conTxt: Context, itsMsg: String?) {
        val isIdsChannel = "damar"
        val isTitleC = "daily notification"

        val neaten = Intent(conTxt, MainActivity::class.java)
        neaten.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val onIntPending = PendingIntent.getActivity(
            conTxt, 0,
            neaten, PendingIntent.FLAG_ONE_SHOT
        )
        val onCompactManagesNotify = conTxt.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val toBoulder = NotificationCompat.Builder(conTxt, isIdsChannel)
            .setSmallIcon(R.drawable.settingc_alm)
            .setContentText(itsMsg)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(onIntPending)
            .setContentTitle("Daily Reminder")
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val isCnl = NotificationChannel(
                isIdsChannel,
                isTitleC,
                NotificationManager.IMPORTANCE_DEFAULT)
            toBoulder.setChannelId(isIdsChannel)
            onCompactManagesNotify.createNotificationChannel(isCnl)
        }
        val onNotify = toBoulder.build()
        onCompactManagesNotify.notify(100, onNotify)
    }

    companion object {
        private const val Id_Day = 100
        // Make Different TimesHere...
        private const val onTimeToday = "09:00"
        // Sudah Saya perbaiki kak..
        const val dayName = "Daily Reminder"
        const val onEnvelop = "message"
        const val itType = "type"
    }


}