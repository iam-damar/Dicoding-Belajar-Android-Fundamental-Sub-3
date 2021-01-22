package com.damars.damarbuildsubmissionke3.settingadapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.damars.damarbuildsubmissionke3.FragmentOnFollowers
import com.damars.damarbuildsubmissionke3.FragmentOnFollowing
import com.damars.damarbuildsubmissionke3.R

class OnPagerSectionsAdapter(private val vConTxt: Context, faGm: FragmentManager) : FragmentPagerAdapter(faGm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int {return 2}

    override fun getPageTitle(pst: Int): CharSequence {
        return vConTxt.resources.getString(tbTitle[pst])
    }
    companion object{
        private val tbTitle = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }
    override fun getItem(pst: Int): Fragment {
        var fragMn: Fragment? = null
        when (pst) {
            0 -> fragMn = FragmentOnFollowers()
            1 -> fragMn = FragmentOnFollowing()
        }
        return fragMn as Fragment
    }
}