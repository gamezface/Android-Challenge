package com.gamezface.domain.exception

sealed class RemoteException(cause: Throwable, code: Int) :
    AppBaseException(cause, code) {

    class HttpClientErrorException(cause: Throwable, code: Int) :
        RemoteException(cause, code)

    class HttpServerErrorException(cause: Throwable, code: Int) :
        RemoteException(cause, code)
}

