package com.gamezface.domain.cast.repository

import com.gamezface.domain.cast.entity.CastDetails

interface CastDetailsRepository {
    suspend fun getCastDetails(id: Long): List<CastDetails>?
}