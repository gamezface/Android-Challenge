package com.gamezface.data.remote.cast.response

import com.gamezface.data.base.BaseResponse
import com.gamezface.domain.cast.entity.Cast

data class CastResponse(
    val person: PersonResponse?,
    val character: PersonResponse?
) : BaseResponse<Cast> {
    override fun toDomain() = Cast(
        person?.toDomain(),
        character?.toDomain()
    )
}