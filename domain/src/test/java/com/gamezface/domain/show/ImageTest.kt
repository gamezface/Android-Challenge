package com.gamezface.domain.show

import com.gamezface.domain.show.entity.Image
import io.mockk.mockk
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ImageTest {
    @Test
    fun imageNullableTest(){
        val image = Image(null, null, null)
        assertNull(image.medium)
        assertNull(image.imdb)
        assertNull(image.original)
        assertNull(image.getImageUrl())
    }
    @Test
    fun imageModelTest(){
        val image = mockImage()
        assertNotNull(image.medium)
        assertNotNull(image.imdb)
        assertNotNull(image.original)
        assertNotNull(image.getImageUrl())
        assertEquals(image.medium, "medium")
        assertEquals(image.imdb, "imdb")
        assertEquals(image.original, "original")
    }

    @Test
    fun getImageUrlTest() {
        var image = mockImage()
        assertEquals(image.getImageUrl(), "original")

        image = Image("medium", "imdb", null)
        assertEquals(image.getImageUrl(), "medium")

        image = Image(null, "imdb", null)
        assertEquals(image.getImageUrl(), "imdb")

    }

    private fun mockImage() = Image("medium", "imdb", "original")
}