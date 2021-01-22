package com.damars.itsconsumapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class Prelude : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.prelude)
        preludeLoop()
    }

    private fun preludeLoop(){
        Handler(Looper.getMainLooper()).postDelayed({
            val intPrelude = Intent(this@Prelude, MainActivity::class.java)
            startActivity(intPrelude)
            finish()
        }, 2200)
    }
}