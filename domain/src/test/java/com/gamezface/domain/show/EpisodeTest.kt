package com.gamezface.domain.show

import com.gamezface.domain.show.entity.Episode
import io.mockk.mockk
import junit.framework.TestCase.*
import org.junit.Test

class EpisodeTest {
    @Test
    fun episodeNullableTest() {
        val episode = Episode(1L, null, null, null, null, null)
        assertNull(episode.name)
        assertNull(episode.season)
        assertNull(episode.number)
        assertNull(episode.image)
        assertNull(episode.summary)
        assertNotNull(episode.id)
        assertEquals(episode.id, 1L)
    }

    @Test
    fun episodeModelTest() {
        val episode = Episode(1L, "John Doe", 1L, 1L, mockk(), "Lorem ipsum")
        assertNotNull(episode.name)
        assertNotNull(episode.season)
        assertNotNull(episode.number)
        assertNotNull(episode.image)
        assertNotNull(episode.summary)
        assertNotNull(episode.id)
        assertEquals(episode.name, "John Doe")
        assertEquals(episode.summary, "Lorem ipsum")
        assertEquals(episode.season, 1L)
        assertEquals(episode.number, 1L)
        assertEquals(episode.id, 1L)
    }
}