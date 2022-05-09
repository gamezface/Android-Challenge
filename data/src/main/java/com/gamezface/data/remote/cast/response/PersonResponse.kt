package com.gamezface.data.remote.cast.response

import com.gamezface.data.base.BaseResponse
import com.gamezface.data.remote.show.response.ImageResponse
import com.gamezface.domain.cast.entity.Person

data class PersonResponse(
    val id: Long,
    val name: String?,
    val image: ImageResponse?
) : BaseResponse<Person> {
    override fun toDomain() = Person(id, name, image?.toDomain())
}
