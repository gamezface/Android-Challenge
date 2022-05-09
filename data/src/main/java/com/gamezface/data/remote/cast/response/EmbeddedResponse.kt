package com.gamezface.data.remote.cast.response

import com.gamezface.data.base.BaseResponse
import com.gamezface.data.remote.show.response.ShowResponse
import com.gamezface.domain.cast.entity.Embedded

data class EmbeddedResponse(
    val show: ShowResponse?,
) : BaseResponse<Embedded> {
    override fun toDomain() =
        Embedded(show?.toDomain())
}
