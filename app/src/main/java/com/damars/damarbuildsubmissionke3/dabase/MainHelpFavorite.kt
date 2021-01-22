package com.damars.damarbuildsubmissionke3.dabase

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.damars.damarbuildsubmissionke3.dabase.MainContractsDb.OnColumnsFavo.Companion.ITSUSERNAME
import com.damars.damarbuildsubmissionke3.dabase.MainContractsDb.OnColumnsFavo.Companion.TabLes_sName
import kotlin.jvm.Throws

class MainHelpFavorite(context: Context) {

    private var hlpBaseDt: MainHelpDb = MainHelpDb(context)
    private var bsOfDt: SQLiteDatabase = hlpBaseDt.writableDatabase

    companion object {
        private const val baseData_Tbl = TabLes_sName
        private var Inst: MainHelpFavorite? = null
        fun getInstance(context: Context): MainHelpFavorite = Inst ?: synchronized(this) {
            Inst ?: MainHelpFavorite(context)
        }
    }

    @Throws(SQLException::class)
    fun toOpeningDaBase() {
        bsOfDt = hlpBaseDt.writableDatabase
    }
    fun toClosingDaBase() {
        hlpBaseDt.close()
        if(bsOfDt.isOpen)
            bsOfDt.close()
    }

    fun qryAll(): Cursor {
        return bsOfDt.query(
                baseData_Tbl,
                null,
                null,
                null,
                null,
                null,
                "$ITSUSERNAME ASC"
        )
    }
    fun qryOnId(id: String): Cursor {
        return bsOfDt.query(
                baseData_Tbl,
                null,
                "$ITSUSERNAME = ?",
                arrayOf(id),
                null,
                null,
                null,
                null
        )
    }

    fun toInserts(values: ContentValues?): Long{
        return bsOfDt.insert(baseData_Tbl, null, values)
    }
    fun toUpDt(id: String, values: ContentValues?): Int {
        return bsOfDt.update(baseData_Tbl, values, "$ITSUSERNAME = ?", arrayOf(id))
    }
    fun toDltOnId(id: String): Int {
        return bsOfDt.delete(baseData_Tbl, "$ITSUSERNAME = '$id'", null)
    }
}