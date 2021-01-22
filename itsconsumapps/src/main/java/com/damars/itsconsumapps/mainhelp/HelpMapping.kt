package com.damars.itsconsumapps.mainhelp

import android.database.Cursor
import com.damars.itsconsumapps.dabase.MainContractsDb
import com.damars.itsconsumapps.linksource.ItsFavorite

object HelpMapping {

    fun cursorMapToListArray(csrOfNot: Cursor?) : ArrayList<ItsFavorite> {
        val lstOfFvr = ArrayList<ItsFavorite>()

        csrOfNot?.apply {
            while(moveToNext()){
                val itsOnsName = getString(getColumnIndexOrThrow(MainContractsDb.OnColumnsFavo.ITSNAME))
                val itsOnsNameUser = getString(getColumnIndexOrThrow(MainContractsDb.OnColumnsFavo.ITSUSERNAME))
                val itsOnsAvTr = getString(getColumnIndexOrThrow(MainContractsDb.OnColumnsFavo.ITSAVATAR))
                val itsOnsCompany = getString(getColumnIndexOrThrow(MainContractsDb.OnColumnsFavo.ITSCOMPANY))
                val itsOnsRepos = getString(getColumnIndexOrThrow(MainContractsDb.OnColumnsFavo.ITSREPOSITORY))
                val itsOnsFvr = getString(getColumnIndexOrThrow(MainContractsDb.OnColumnsFavo.ITSFAVORITE))
                val itsOnsLocates = getString(getColumnIndexOrThrow(MainContractsDb.OnColumnsFavo.ITSLOCATION))
                lstOfFvr.add(
                    ItsFavorite(
                        itsOnsNameUser,
                        itsOnsName,
                        itsOnsAvTr,
                        itsOnsLocates,
                        itsOnsCompany,
                        itsOnsRepos,
                        itsOnsFvr
                    )
                )
            }
        }
        return lstOfFvr
    }
}