package com.gamezface.data.remote.show.response

import com.gamezface.data.base.BaseResponse
import com.gamezface.data.remote.cast.response.CastResponse
import com.gamezface.domain.show.entity.Embedded

data class EmbeddedResponse(
    val episodes: List<EpisodeResponse>?,
    val cast: List<CastResponse>?,
) : BaseResponse<Embedded> {
    override fun toDomain() =
        Embedded(episodes?.map { it.toDomain() }, cast?.map { it.toDomain() })
}
