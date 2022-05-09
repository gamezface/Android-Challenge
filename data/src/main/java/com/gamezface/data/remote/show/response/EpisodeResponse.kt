package com.gamezface.data.remote.show.response

import com.gamezface.data.base.BaseResponse
import com.gamezface.domain.show.entity.Episode

data class EpisodeResponse(
    val id: Long,
    val name: String?,
    val season: Long?,
    val number: Long?,
    val image: ImageResponse?,
    val summary: String?
) : BaseResponse<Episode> {
    override fun toDomain() = Episode(
        id,
        name,
        season,
        number,
        image?.toDomain(),
        summary
    )
}
