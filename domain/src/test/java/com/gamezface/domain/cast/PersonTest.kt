package com.gamezface.domain.cast

import com.gamezface.domain.cast.entity.Person
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Test

class PersonTest {
    @Test
    fun personModelTest() {
        val person = Person(id = 1L, "John Doe", mockk())
        assertNotNull(person.image)
        assertNotNull(person.name)
        assertNotNull(person.id)
        assertEquals(person.name, "John Doe")
        assertEquals(person.id, 1L)
    }
}