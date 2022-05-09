package com.gamezface.data.remote.cast.response

import com.gamezface.data.base.BaseResponse
import com.gamezface.domain.cast.entity.CastDetails
import com.google.gson.annotations.SerializedName

class CastDetailsResponse(
    @SerializedName("_embedded")
    private val embedded: EmbeddedResponse?,
) : BaseResponse<CastDetails> {
    override fun toDomain() = CastDetails(
        embedded?.toDomain()
    )
}