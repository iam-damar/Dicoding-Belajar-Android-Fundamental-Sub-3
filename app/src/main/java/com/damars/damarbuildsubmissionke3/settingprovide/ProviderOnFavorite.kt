package com.damars.damarbuildsubmissionke3.settingprovide

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.damars.damarbuildsubmissionke3.dabase.MainContractsDb.MAUTH
import com.damars.damarbuildsubmissionke3.dabase.MainContractsDb.OnColumnsFavo.Companion.CONTENTS_OF_URI
import com.damars.damarbuildsubmissionke3.dabase.MainContractsDb.OnColumnsFavo.Companion.TabLes_sName
import com.damars.damarbuildsubmissionke3.dabase.MainHelpFavorite

class ProviderOnFavorite : ContentProvider() {
    companion object {
        private const val OnFV = 1
        private const val OnFvId = 2
        private lateinit var mainHlpFv: MainHelpFavorite
        private val urisMatchS = UriMatcher(UriMatcher.NO_MATCH)

        init {
            urisMatchS.addURI(MAUTH, TabLes_sName, OnFV)
            urisMatchS.addURI(
                MAUTH,
                "$TabLes_sName/#",
                OnFvId
            )
        }
    }
    override fun delete(uri: Uri, t: String?, ts: Array<String>?): Int {
        val inDlTs: Int = when (OnFvId) {
            urisMatchS.match(uri) -> mainHlpFv.toDltOnId(uri.lastPathSegment.toString())
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENTS_OF_URI, null)
        return inDlTs
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(onUri: Uri, contentValues: ContentValues?): Uri? {
        val inAds: Long = when (OnFV) {
            urisMatchS.match(onUri) -> mainHlpFv.toInserts(contentValues)
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENTS_OF_URI, null)
        return Uri.parse("$CONTENTS_OF_URI/$inAds")
    }

    override fun onCreate(): Boolean {
        mainHlpFv = MainHelpFavorite.getInstance(context as Context)
        mainHlpFv.toOpeningDaBase()
        return true
    }

    override fun query(
        uri: Uri,
        ts: Array<String>?,
        t: String?,
        ts1: Array<String>?,
        tst1: String?
    ): Cursor? {
        return when(urisMatchS.match(uri)) {
            OnFV -> mainHlpFv.qryAll()
            OnFvId -> mainHlpFv.qryOnId(uri.lastPathSegment.toString())
            else -> null
        }
    }

    override fun update(onUri: Uri, values: ContentValues?, t: String?, ts: Array<String>?): Int {
        val inUpDt: Int = when(OnFvId) {
            urisMatchS.match(onUri) -> mainHlpFv.toUpDt(
                onUri.lastPathSegment.toString(),values
            )
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENTS_OF_URI, null)
        return inUpDt
    }
}