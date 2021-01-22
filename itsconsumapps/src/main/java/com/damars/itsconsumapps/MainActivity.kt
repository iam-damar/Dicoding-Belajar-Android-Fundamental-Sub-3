package com.damars.itsconsumapps

import android.content.Intent
import android.database.ContentObserver
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.damars.itsconsumapps.dabase.MainContractsDb.OnColumnsFavo.Companion.CONTENTS_OF_URI
import com.damars.itsconsumapps.linksource.ItsFavorite
import com.damars.itsconsumapps.mainhelp.HelpMapping
import com.damars.itsconsumapps.settingadapter.MainFavoriteAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            supportActionBar?.title=  getString(R.string.tlt)
        }
        viewRecycleToFavorite.layoutManager = LinearLayoutManager(this)
        fvAdPtr = MainFavoriteAdapter(this)
        viewRecycleToFavorite.adapter = fvAdPtr

        val handTrd = HandlerThread("DataObserver")
        viewRecycleToFavorite.setHasFixedSize(true)
        handTrd.start()
        val hnl = Handler(handTrd.looper)
        val itsObserve = object : ContentObserver(hnl){
            override fun onChange(self: Boolean) { markLoadOnsAsync() }
        }
        contentResolver.registerContentObserver(CONTENTS_OF_URI, true, itsObserve)
        if(savedInstanceState == null) {
            markLoadOnsAsync()
        } else {
            val toLst = savedInstanceState.getParcelableArrayList<ItsFavorite>(MountStats)
            if(toLst != null){ fvAdPtr.lstItFvRite = toLst }
        }
    }

    private lateinit var fvAdPtr: MainFavoriteAdapter
    companion object {
        private const val MountStats = "mount_stats" }

    private fun markLoadOnsAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            barProgressToFavorite.visibility = View.VISIBLE
            val notSPatch = async(Dispatchers.IO) {
                val mainCrs = contentResolver?.query(CONTENTS_OF_URI, null, null, null, null)
                HelpMapping.cursorMapToListArray(mainCrs)
            }
            val dtsFv = notSPatch.await()
            barProgressToFavorite.visibility = View.INVISIBLE
            if(dtsFv.size > 0){
                fvAdPtr.lstItFvRite = dtsFv
            } else {
                fvAdPtr.lstItFvRite = ArrayList()
                onBarMsgShow()
            }
        }
    }


    override fun onSaveInstanceState(outOnStat: Bundle) {
        super.onSaveInstanceState(outOnStat)
        outOnStat.putParcelableArrayList(MountStats, fvAdPtr.lstItFvRite)
    }

    private fun onBarMsgShow() {
        Toast.makeText(this, getString(R.string.blank_favorite), Toast.LENGTH_SHORT).show() }

    override fun onResume() {
        super.onResume()
        markLoadOnsAsync()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.implicit_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(isTem: MenuItem): Boolean {
        when(isTem.itemId){
            R.id.ItsMe -> {
                val itsMe = Intent(this, PageOfMe::class.java)
                startActivity(itsMe)
            }
            R.id.setOnLanguage_change -> {
                val langInt = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(langInt)
            }
        }
        return super.onOptionsItemSelected(isTem)
    }
}