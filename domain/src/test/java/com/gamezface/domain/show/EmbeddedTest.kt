package com.gamezface.domain.show

import com.gamezface.domain.show.entity.Embedded
import io.mockk.mockk
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import org.junit.Test

class EmbeddedTest {
    @Test
    fun embeddedNullableTest(){
        val embedded = Embedded(null, null)
        assertNull(embedded.cast)
        assertNull(embedded.episodes)
    }
    @Test
    fun embeddedModelTest(){
        val embedded = Embedded(mockk(), mockk())
        assertNotNull(embedded.cast)
        assertNotNull(embedded.episodes)
    }
}