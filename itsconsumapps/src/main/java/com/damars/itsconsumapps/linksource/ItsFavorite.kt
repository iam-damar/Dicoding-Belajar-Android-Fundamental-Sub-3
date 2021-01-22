package com.damars.itsconsumapps.linksource

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItsFavorite (
    var itsUsername: String? = null,
    var itsName: String? = null,
    var itsAvatar: String? = null,
    var itsLocation: String? = null,
    var itsCompany: String? = null,
    var itsRepository: String? = null,
    var itsFollower: String? = null,
    var itsFollowing: String? = null,
    var itsFavor: String? = null
): Parcelable