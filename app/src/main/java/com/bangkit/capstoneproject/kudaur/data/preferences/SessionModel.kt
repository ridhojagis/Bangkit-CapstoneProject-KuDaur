package com.bangkit.capstoneproject.kudaur.data.preferences

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SessionModel (
    var userId: String? = null,
    var name: String? = null,
    var token: String? = null
) : Parcelable
