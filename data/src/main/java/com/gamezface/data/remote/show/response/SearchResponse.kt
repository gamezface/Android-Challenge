package com.gamezface.data.remote.show.response

import com.gamezface.data.base.BaseResponse
import com.gamezface.data.remote.cast.response.PersonResponse
import com.gamezface.domain.show.entity.Search

data class SearchResponse(
    val show: ShowResponse?,
    val score: Double?,
    val person: PersonResponse?
) : BaseResponse<Search> {
    override fun toDomain() = Search(show?.toDomain(), person?.toDomain(), score)
}

