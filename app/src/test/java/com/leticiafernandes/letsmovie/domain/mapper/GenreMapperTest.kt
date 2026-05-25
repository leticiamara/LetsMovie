package com.leticiafernandes.letsmovie.domain.mapper

import com.leticiafernandes.letsmovie.domain.model.Genre
import org.junit.Assert.assertEquals
import org.junit.Test

class GenreMapperTest {

    @Test
    fun `mapToGenreItem correctly maps id and name`() {
        val genre = Genre(id = 28L, name = "Action")

        val item = genre.mapToGenreItem()

        assertEquals(genre.id, item.id)
        assertEquals(genre.name, item.name)
    }

    @Test
    fun `mapToGenreItem preserves empty name`() {
        val genre = Genre(id = 0L, name = "")

        val item = genre.mapToGenreItem()

        assertEquals(0L, item.id)
        assertEquals("", item.name)
    }
}
