package com.gamezface.domain.cast.entity

class Cast(val person: Person?, val character: Person?) {
    fun getImageUrl(): String? {
        return person?.image?.getImageUrl() ?: character?.image?.getImageUrl()
    }
}