package com.gamezface.domain.cast

import com.gamezface.domain.cast.entity.Cast
import com.gamezface.domain.cast.entity.Person
import com.gamezface.domain.show.entity.Image
import junit.framework.TestCase.*
import org.junit.Test

class CastTest {
    @Test
    fun castNullableModelTest() {
        val cast = Cast(null, null)
        assertNull(cast.person)
        assertNull(cast.character)
    }
    @Test
    fun castModelTest() {
        val cast = Cast(person = mockPerson(), character = mockPerson())
        assertNotNull(cast.person)
        assertNotNull(cast.character)
        assertEquals(cast.person?.id, 1L)
        assertEquals(cast.person?.name, "John Doe")
        assertEquals(cast.person?.image?.imdb, "imdb")
        assertEquals(cast.person?.image?.original, "original")
        assertEquals(cast.person?.image?.medium, "medium")

        assertEquals(cast.character?.id, 1L)
        assertEquals(cast.character?.name, "John Doe")
        assertEquals(cast.character?.image?.imdb, "imdb")
        assertEquals(cast.character?.image?.original, "original")
        assertEquals(cast.character?.image?.medium, "medium")
    }

    @Test
    fun castGetImageTest() {
        var cast = Cast(person = mockPerson(), character = null)
        assertNotNull(cast.getImageUrl())
        assertEquals(cast.getImageUrl(), "original")

        cast = Cast(person = null, character = mockPerson())
        assertNotNull(cast.getImageUrl())
        assertEquals(cast.getImageUrl(), "original")
    }

    private fun mockPerson() = Person(1L, "John Doe", Image("medium","imdb","original"))
}