package com.damars.itsconsumapps.dabase

import android.net.Uri
import android.provider.BaseColumns

object MainContractsDb {

    const val MAUTH = "com.damars.damarbuildsubmissionke3"
    const val SCHE = "content"

    class OnColumnsFavo : BaseColumns {
        companion object{
            const val TabLes_sName = "itsFavorite"
            const val ITSUSERNAME = "itsUsername"
            const val ITSNAME = "itsName"
            const val ITSAVATAR = "itsAvatar"
            const val ITSLOCATION = "itsLocation"
            const val ITSCOMPANY = "itsCompany"
            const val ITSREPOSITORY = "itsRepository"
            const val ITSFAVORITE = "itsFavorite"

            val CONTENTS_OF_URI: Uri = Uri.Builder().scheme(SCHE)
                .authority(MAUTH)
                .appendPath(TabLes_sName)
                .build()
        }
    }
}