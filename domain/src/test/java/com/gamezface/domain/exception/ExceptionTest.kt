package com.gamezface.domain.exception

import io.mockk.mockk
import junit.framework.TestCase.*
import org.junit.Test

class ExceptionTest {
    @Test
    fun networkExceptionTest() {
        val exception = AppNetworkException(Throwable("foo", mockk()))
        assertNotNull(exception)
        assertNotNull(exception.cause)
        assertNotNull(exception.message)
        assertNull(exception.code)
        assertEquals(exception.message, "foo")
    }

    @Test
    fun remoteClientExceptionTest() {
        val exception = RemoteException.HttpClientErrorException(Throwable("foo", mockk()), 404)
        assertNotNull(exception)
        assertNotNull(exception.cause)
        assertNotNull(exception.message)
        assertNotNull(exception.code)
        assertEquals(exception.message, "foo")
        assertEquals(exception.code, 404)
    }
    @Test
    fun remoteServerExceptionTest() {
        val exception = RemoteException.HttpServerErrorException(Throwable("foo", mockk()), 500)
        assertNotNull(exception)
        assertNotNull(exception.cause)
        assertNotNull(exception.message)
        assertNotNull(exception.code)
        assertEquals(exception.message, "foo")
        assertEquals(exception.code, 500)
    }
}