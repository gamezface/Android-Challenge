package com.gamezface.domain.cast.entity

import android.os.Parcelable
import com.gamezface.domain.show.entity.Image
import kotlinx.parcelize.Parcelize

@Parcelize
data class Person(
    val id: Long,
    val name: String?,
    val image: Image?,
) : Parcelable