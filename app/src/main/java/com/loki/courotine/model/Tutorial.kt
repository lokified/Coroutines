package com.loki.courotine.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tutorial(
    val name: String,
    val url: String,
    val description: String
): Parcelable