package com.gamezface.domain.show

import com.gamezface.domain.show.entity.Search
import io.mockk.mockk
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertEquals
import org.junit.Test

class SearchTest {
    @Test
    fun searchNullableTest(){
        val search = Search(null, null, null)
        assertNull(search.show)
        assertNull(search.person)
        assertNull(search.score)
    }
    @Test
    fun searchModelTest(){
        val search = Search(mockk(), mockk(), 1.5)
        assertNotNull(search.show)
        assertNotNull(search.person)
        assertNotNull(search.score)
        assertEquals(search.score, 1.5)
    }
}