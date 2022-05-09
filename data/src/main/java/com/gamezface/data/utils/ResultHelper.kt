package com.gamezface.data.utils

import com.gamezface.data.base.BaseResponse
import com.gamezface.domain.exception.RemoteException

suspend inline fun <D, reified T : BaseResponse<D>> getList(noinline call: suspend () -> Any?): List<D>? {
    try {
        return parseArrayFromJson<T>(call())?.map { it.toDomain() }
    } catch (httpException: RemoteException.HttpClientErrorException) {
        if (httpException.code == HttpCodes.NOT_FOUND) {
            return emptyList()
        }
        throw httpException
    } catch (e: Exception) {
        throw e
    }
}

suspend inline fun <D, reified T : BaseResponse<D>> getObject(noinline call: suspend () -> Any?): D? {
    try {
        return parseObjectFromJson<T>(call())?.toDomain()
    } catch (e: Exception) {
        throw e
    }
}