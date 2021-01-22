package com.damars.damarbuildsubmissionke3

import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.damars.damarbuildsubmissionke3.dabase.MainContractsDb.OnColumnsFavo.Companion.CONTENTS_OF_URI
import com.damars.damarbuildsubmissionke3.linksource.ItsFavorite
import com.damars.damarbuildsubmissionke3.mainhelp.HelpMapping
import com.damars.damarbuildsubmissionke3.settingadapter.MainFavoriteAdapter
import kotlinx.android.synthetic.main.main_page_on_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainPageOnFavorite : AppCompatActivity() {

    private lateinit var fvAdPtr: MainFavoriteAdapter
    companion object {
        private const val MountStats = "mount_stats" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_page_on_favorite)
        supportActionBar?.title = getString(R.string.lst)

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
        Toast.makeText(this, getString(R.string.blank_favorite),Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        markLoadOnsAsync()
    }
}