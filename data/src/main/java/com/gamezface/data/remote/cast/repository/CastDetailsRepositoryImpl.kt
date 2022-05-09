package com.gamezface.data.remote.cast.repository

import com.gamezface.data.remote.cast.response.CastDetailsResponse
import com.gamezface.data.remote.cast.source.CastDetailsRemoteDataSource
import com.gamezface.data.utils.getList
import com.gamezface.domain.cast.entity.CastDetails
import com.gamezface.domain.cast.repository.CastDetailsRepository
import javax.inject.Inject

class CastDetailsRepositoryImpl @Inject constructor(
    private val remoteDataSource: CastDetailsRemoteDataSource
) : CastDetailsRepository {

    override suspend fun getCastDetails(id: Long): List<CastDetails>? =
        getList<CastDetails, CastDetailsResponse> {
            remoteDataSource.getCastDetails(id)
        }
}