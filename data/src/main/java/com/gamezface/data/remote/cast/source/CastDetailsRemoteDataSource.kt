package com.gamezface.data.remote.cast.source

import com.gamezface.data.base.BaseRemoteDataSource
import com.gamezface.data.remote.api.ProjectApi
import com.gamezface.data.utils.Endpoints
import com.gamezface.data.utils.QueryParams
import javax.inject.Inject

class CastDetailsRemoteDataSource @Inject constructor(
    private val projectApi: ProjectApi
) : BaseRemoteDataSource() {

    suspend fun getCastDetails(id: Long) = getResult {
        projectApi.get(
            url = Endpoints.PEOPLE.plus(id).plus(Endpoints.CAST_CREDITS),
            queries = mapOf(QueryParams.EMBED to QueryParams.SHOW)
        )
    }

}