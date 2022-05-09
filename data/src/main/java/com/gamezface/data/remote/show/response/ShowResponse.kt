package com.gamezface.data.remote.show.response

import com.gamezface.data.base.BaseResponse
import com.gamezface.domain.show.entity.Show
import com.google.gson.annotations.SerializedName

data class ShowResponse(
    val id: Long?,
    val name: String?,
    val genres: List<String>?,
    val averageRuntime: Int?,
    val schedule: ScheduleResponse?,
    val image: ImageResponse?,
    val summary: String?,
    @SerializedName("_embedded")
    val embedded: EmbeddedResponse?
) : BaseResponse<Show> {
    override fun toDomain() = Show(
        id,
        name,
        genres,
        averageRuntime,
        schedule?.toDomain(),
        image?.toDomain(),
        summary,
        embedded?.toDomain()
    )
}
