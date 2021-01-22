package com.damars.damarbuildsubmissionke3.dabase

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.damars.damarbuildsubmissionke3.dabase.MainContractsDb.OnColumnsFavo.Companion.TabLes_sName

internal class MainHelpDb(itsConTxt: Context) : SQLiteOpenHelper(itsConTxt, DaBase_sName, null, DaBase_sVersion) {
    companion object{
        private const val DaBase_sName = "itsDaBaseUser"
        private const val DaBase_sVersion = 1
        private const val SQL_CREATE_NOTE_OF_TABLE = "CREATE TABLE $TabLes_sName" +
                "(${MainContractsDb.OnColumnsFavo.ITSUSERNAME} TEXT PRIMARY KEY NOT NULL, " + "${MainContractsDb.OnColumnsFavo.ITSNAME} TEXT NOT NULL, " +
                "${MainContractsDb.OnColumnsFavo.ITSAVATAR} TEXT NOT NULL, " + "${MainContractsDb.OnColumnsFavo.ITSLOCATION} TEXT NOT NULL, " +
                "${MainContractsDb.OnColumnsFavo.ITSCOMPANY} TEXT NOT NULL, " + "${MainContractsDb.OnColumnsFavo.ITSREPOSITORY} TEXT NOT NULL, " + "${MainContractsDb.OnColumnsFavo.ITSFAVORITE} TEXT NOT NULL)"
    }

    override fun onCreate(dabase: SQLiteDatabase) {
        dabase.execSQL(SQL_CREATE_NOTE_OF_TABLE) }

    override fun onUpgrade(dabase: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        dabase.execSQL("DROP TABLE IF EXISTS $TabLes_sName")
        onCreate(dabase) }
}