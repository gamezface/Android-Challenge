package com.gamezface.domain.show.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(val medium: String?, val imdb: String?, val original: String?) : Parcelable {
    fun getImageUrl() = original ?: medium ?: imdb
}
