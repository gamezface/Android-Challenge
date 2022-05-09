package com.gamezface.domain.cast

import com.gamezface.domain.cast.entity.CastDetails
import io.mockk.mockk
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import org.junit.Test

class CastDetailsTest {
    @Test
    fun castDetailsNullableModelTest() {
        val castDetails = CastDetails(null)
        assertNull(castDetails.embedded)
    }

    @Test
    fun castDetailsModelTest() {
        val castDetails = CastDetails(mockk())
        assertNotNull(castDetails.embedded)
    }
}