package com.gamezface.data.remote.show.response

import com.gamezface.data.base.BaseResponse
import com.gamezface.domain.show.entity.Image

data class ImageResponse(
    val medium: String?,
    val imdb: String?,
    val original: String?
) : BaseResponse<Image> {
    override fun toDomain() = Image(medium, imdb, original)
}