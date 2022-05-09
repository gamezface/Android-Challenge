package com.gamezface.domain.show.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Episode(
    val id: Long,
    val name: String?,
    val season: Long?,
    val number: Long?,
    val image: Image?,
    val summary: String?
) : Parcelable