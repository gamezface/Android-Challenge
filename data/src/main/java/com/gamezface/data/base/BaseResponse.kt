package com.gamezface.data.base

interface BaseResponse<D> {
    fun toDomain(): D
}