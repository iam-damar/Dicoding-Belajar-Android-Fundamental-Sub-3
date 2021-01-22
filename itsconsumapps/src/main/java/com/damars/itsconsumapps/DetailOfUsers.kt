package com.damars.itsconsumapps

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.damars.itsconsumapps.linksource.ItsFavorite
import com.damars.itsconsumapps.settingadapter.OnPagerSectionsAdapter
import kotlinx.android.synthetic.main.detail_of_users.*

class DetailOfUsers : AppCompatActivity() {

    private lateinit var avaTrIsImg: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_of_users)
        setOnsObjectD()
        confSetOnPgView()
    }

    private fun confSetOnPgView() {
        val pagerOnAdapterView = OnPagerSectionsAdapter(this, supportFragmentManager)
        itsViewDetailPager.adapter = pagerOnAdapterView
        on_LayoutTabs.setupWithViewPager(itsViewDetailPager)
        supportActionBar?.elevation = 0f
    }


    private fun setOnsObjectD() {
        val inputToUsrOnFv = intent.getParcelableExtra<ItsFavorite>(MountOnNot) as ItsFavorite
        isDetails_onName.text = inputToUsrOnFv.itsName
        isDetails_onsUsername.text = inputToUsrOnFv.itsUsername
        Glide.with(this)
            .load(inputToUsrOnFv.itsAvatar)
            .into(isDetails_onAvatarsImg)
        avaTrIsImg = inputToUsrOnFv.itsAvatar.toString()
        isDetails_onsLocation.text = inputToUsrOnFv.itsLocation
        isDetails_onsCompany.text = inputToUsrOnFv.itsCompany
        isDetails_onsRepository.text = inputToUsrOnFv.itsRepository
    }

    companion object {
        const val MountOnNot = "mount_note"
        const val MountOnPos = "mount_position"
    }

    override fun onCreateOptionsMenu(mn: Menu): Boolean {
        menuInflater.inflate(R.menu.sec_imp_menu, mn)
        return super.onCreateOptionsMenu(mn)
    }

    override fun onOptionsItemSelected(isDItem: MenuItem): Boolean {
        when(isDItem.itemId){
            R.id.setOnLanguage_change -> {
                val languageInt = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(languageInt)
            }
            R.id.ItsMe -> {
                val itsMe = Intent(this@DetailOfUsers, PageOfMe::class.java)
                startActivity(itsMe)
            }
        }
        return super.onOptionsItemSelected(isDItem)
    }
}