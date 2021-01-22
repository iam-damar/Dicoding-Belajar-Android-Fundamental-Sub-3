package com.damars.itsconsumapps

import android.os.Bundle
import android.widget.CalendarView
import androidx.appcompat.app.AppCompatActivity

class PageOfMe : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_of_me)
        MeShw()
        Cln()
    }
    private lateinit var clnView: CalendarView
    private fun Cln() {
        clnView = findViewById(R.id.itsCal)
        clnView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            dayOfMonth.toString() + "-" + (month + 1) + "-" + year
        }
    }
    private fun MeShw(){
        val meBar = supportActionBar
        if(meBar != null) {
            meBar.title = getString(R.string.its_me)
        }
    }
}