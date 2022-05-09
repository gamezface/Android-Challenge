package com.gamezface.domain.show

import com.gamezface.domain.show.entity.Show
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertNotNull
import org.junit.Test

class ShowTest {
    @Test
    fun showNullableTest(){
        val show = Show(null, null, null, null, null, null, null, null)
        assertNull(show.id)
        assertNull(show.name)
        assertNull(show.genres)
        assertNull(show.getGenres())
        assertNull(show.averageRuntime)
        assertNull(show.schedule)
        assertNull(show.image)
        assertNull(show.summary)
        assertNull(show.embedded)
    }
    @Test
    fun showModelTest(){
        val show = Show(1L, "foo", listOf("foo", "bar"), 1, mockk(), mockk(), "Lorem", mockk())
        assertNotNull(show.id)
        assertNotNull(show.name)
        assertNotNull(show.genres)
        assertNotNull(show.getGenres())
        assertNotNull(show.averageRuntime)
        assertNotNull(show.schedule)
        assertNotNull(show.image)
        assertNotNull(show.summary)
        assertNotNull(show.embedded)

        assertEquals(show.id, 1L)
        assertEquals(show.name, "foo")
        assert(!show.genres.isNullOrEmpty())
        assertEquals(show.genres?.first(), "foo")
        assertEquals(show.genres?.last(), "bar")
        assertEquals(show.getGenres(), "foo, bar")
        assertEquals(show.averageRuntime, 1)
        assertEquals(show.summary, "Lorem")
    }
}