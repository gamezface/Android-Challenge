package com.gamezface.domain.exception

abstract class AppBaseException(
    cause: Throwable,
    val code: Int? = null
) : Exception(cause.message, cause)