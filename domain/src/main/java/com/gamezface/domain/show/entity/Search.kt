package com.gamezface.domain.show.entity

import com.gamezface.domain.cast.entity.Person

data class Search(val show: Show?, val person: Person?, val score: Double?) {
    fun getName() = show?.name ?: person?.name
    fun getImage() = show?.image?.getImageUrl() ?: person?.image?.getImageUrl()
}