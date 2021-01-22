package com.damars.damarbuildsubmissionke3

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.damars.damarbuildsubmissionke3.reminder.RemindOnsAlarm
import kotlinx.android.synthetic.main.settings_notify.*

class SettingsNotify : AppCompatActivity() {

    private lateinit var rmdOnsAlarm: RemindOnsAlarm
    private lateinit var  rfsOnShares: SharedPreferences

    companion object {
        const val setNamesRef = "Name Notify"
        private const val OnsDay = "onsDay"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_notify)

        supportActionBar?.title = getString(R.string.set_onRemind)
        rmdOnsAlarm = RemindOnsAlarm()
        rfsOnShares = getSharedPreferences(setNamesRef, Context.MODE_PRIVATE)

        setFltSwch()
        cStWidget.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                rmdOnsAlarm.setAlmRemind(
                    this, RemindOnsAlarm.dayName, getString(R.string.d_message))
            } else {
                rmdOnsAlarm.shutDownAlm(this) }
            isKeep(isChecked)
        }
    }

    override fun onOptionsItemSelected(isItm: MenuItem): Boolean {
        if(isItm.itemId == R.id.home)
            finish()
        return super.onOptionsItemSelected(isItm)
    }
    private fun setFltSwch() {
        cStWidget.isChecked = rfsOnShares.getBoolean(OnsDay, false)
    }

    private fun isKeep(vl: Boolean) {
        val isEdt = rfsOnShares.edit()
        isEdt.putBoolean(OnsDay, vl)
        isEdt.apply()
    }
}