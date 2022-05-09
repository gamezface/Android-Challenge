package com.gamezface.data.base

import com.gamezface.domain.exception.AppNetworkException
import com.gamezface.domain.exception.RemoteException
import retrofit2.HttpException
import java.io.IOException

abstract class BaseRemoteDataSource {
    protected suspend fun <T : Any> getResult(call: suspend () -> T?): T? {
        try {
            return call()
        } catch (e: HttpException) {
            val exception = when (e.code()) {
                in 400..499 -> RemoteException.HttpClientErrorException(e, e.code())
                else -> RemoteException.HttpServerErrorException(e, e.code())
            }
            throw exception
        } catch (e: IOException) {
            throw AppNetworkException(e)
        } catch (e: Exception) {
            throw e
        }
    }
}