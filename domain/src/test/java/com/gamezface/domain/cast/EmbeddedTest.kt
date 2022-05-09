package com.gamezface.domain.cast

import com.gamezface.domain.cast.entity.Embedded
import io.mockk.mockk
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import org.junit.Test

class EmbeddedTest {
    @Test
    fun embeddedNullableModelTest() {
        val embedded = Embedded(null)
        assertNull(embedded.show)
    }

    @Test
    fun embeddedModelTest() {
        val embedded = Embedded(mockk())
        assertNotNull(embedded.show)
    }
}