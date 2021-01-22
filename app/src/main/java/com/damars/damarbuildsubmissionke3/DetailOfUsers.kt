package com.damars.damarbuildsubmissionke3

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.damars.damarbuildsubmissionke3.dabase.MainContractsDb.OnColumnsFavo.Companion.CONTENTS_OF_URI
import com.damars.damarbuildsubmissionke3.dabase.MainContractsDb.OnColumnsFavo.Companion.ITSAVATAR
import com.damars.damarbuildsubmissionke3.dabase.MainContractsDb.OnColumnsFavo.Companion.ITSCOMPANY
import com.damars.damarbuildsubmissionke3.dabase.MainContractsDb.OnColumnsFavo.Companion.ITSFAVORITE
import com.damars.damarbuildsubmissionke3.dabase.MainContractsDb.OnColumnsFavo.Companion.ITSLOCATION
import com.damars.damarbuildsubmissionke3.dabase.MainContractsDb.OnColumnsFavo.Companion.ITSNAME
import com.damars.damarbuildsubmissionke3.dabase.MainContractsDb.OnColumnsFavo.Companion.ITSREPOSITORY
import com.damars.damarbuildsubmissionke3.dabase.MainContractsDb.OnColumnsFavo.Companion.ITSUSERNAME
import com.damars.damarbuildsubmissionke3.dabase.MainHelpFavorite
import com.damars.damarbuildsubmissionke3.linksource.ItsDataUser
import com.damars.damarbuildsubmissionke3.linksource.ItsFavorite
import com.damars.damarbuildsubmissionke3.settingadapter.OnPagerSectionsAdapter
import kotlinx.android.synthetic.main.detail_of_users.*

class DetailOfUsers : AppCompatActivity(), View.OnClickListener {

    private var itWasFv = false
    private lateinit var iGtOnHelpMain: MainHelpFavorite
    private var itsFv: ItsFavorite? = null
    private lateinit var avaTrIsImg: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_of_users)
        iGtOnHelpMain = MainHelpFavorite.getInstance(applicationContext)
        iGtOnHelpMain.toOpeningDaBase()
        itsFv = intent.getParcelableExtra(MountOnNot)
        if(itsFv != null) {
            setOnsObjectD()
            itWasFv = true
            val checked: Int = R.drawable.on_full_star
            tv_onsFavorDetail.setImageResource(checked)
        }else {
            setOnsData()
        }
        title = "Detail Of User"
        confSetOnPgView()
        tv_onsFavorDetail.setOnClickListener(this)
    }

    private fun confSetOnPgView() {
        val pagerOnAdapterView = OnPagerSectionsAdapter(this, supportFragmentManager)
        itsViewDetailPager.adapter = pagerOnAdapterView
        on_LayoutTabs.setupWithViewPager(itsViewDetailPager)
        supportActionBar?.elevation = 0f
    }


    private fun setOnsData() {
        val mainsDtOnUsr = intent.getParcelableExtra<ItsDataUser>(MountOnData) as ItsDataUser
        isDetails_onsUsername.text = mainsDtOnUsr.itsUsername
        isDetails_onName.text = mainsDtOnUsr.itsName
        Glide.with(this)
            .load(mainsDtOnUsr.itsAvatar)
            .into(isDetails_onAvatarsImg)
        avaTrIsImg = mainsDtOnUsr.itsAvatar.toString()
        isDetails_onsCompany.text = getString(R.string.company, mainsDtOnUsr.itsCompany)
        isDetails_onsRepository.text = getString(R.string.repoSt, mainsDtOnUsr.itsRepository)
        isDetails_onsLocation.text = getString(R.string.location, mainsDtOnUsr.itsLocation)
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
        const val MountOnData = "mount_data"
        const val MountOnFv = "mount_data"
        const val MountOnNot = "mount_note"
        const val MountOnPos = "mount_position"
    }

    override fun onDestroy() {
        super.onDestroy()
        iGtOnHelpMain.toClosingDaBase()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.sec_imp_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onClick(view: View) {
        val checked: Int = R.drawable.on_full_star
        val unChecked: Int = R.drawable.on_border_star
        if(view.id == R.id.tv_onsFavorDetail){
            if(itWasFv){
                iGtOnHelpMain.toDltOnId(itsFv?.itsUsername.toString())
                Toast.makeText(this, getString(R.string.deleted_onFavorite), Toast.LENGTH_SHORT).show()
                tv_onsFavorDetail.setImageResource(unChecked)
                itWasFv = false
            } else {
                val mainFUsername = isDetails_onsUsername.text.toString()
                val mainFName = isDetails_onName.text.toString()
                val mainFAvaTr = avaTrIsImg
                val mainFCompany = isDetails_onsCompany.text.toString()
                val mainFLocateS = isDetails_onsLocation.text.toString()
                val mainFRepos = isDetails_onsRepository.text.toString()
                val mainFFv = "1"

                val onVls = ContentValues()
                onVls.put(ITSUSERNAME, mainFUsername)
                onVls.put(ITSNAME, mainFName)
                onVls.put(ITSAVATAR, mainFAvaTr)
                onVls.put(ITSLOCATION, mainFLocateS)
                onVls.put(ITSCOMPANY, mainFCompany)
                onVls.put(ITSREPOSITORY, mainFRepos)
                onVls.put(ITSFAVORITE, mainFFv)
                itWasFv = true
                contentResolver.insert(CONTENTS_OF_URI, onVls)
                Toast.makeText(this, getString(R.string.added_onFavorite), Toast.LENGTH_SHORT).show()
                tv_onsFavorDetail.setImageResource(checked)
            }
        }
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