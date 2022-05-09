package com.gamezface.presentation.viewmodels.home

import com.gamezface.domain.show.entity.Show
import com.gamezface.presentation.viewmodels.BaseViewModelTest
import io.mockk.mockk
import junit.framework.TestCase.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Captor
import org.mockito.Mockito
import org.mockito.kotlin.capture
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class FavoritesViewModelTest : BaseViewModelTest() {

    private val viewModel by lazy {
        FavoritesViewModel(showRepository)
    }

    @Before
    override fun setup() {
        super.setup()
        runBlocking {
            Mockito.`when`(showRepository.retrieveFavorites()).thenReturn(mockShowList())
        }
    }

    @Test
    fun favoritesTest() {
        assertNull(viewModel.getFavorites().value)

        runBlocking {
            viewModel.retrieveFavorites()
            verify(showRepository, times(1)).retrieveFavorites()
            assertNotNull(viewModel.getFavorites().value)
            assertFalse(viewModel.getFavorites().value.isNullOrEmpty())
            assertEquals(viewModel.getFavorites().value?.first()?.name, "bar")
            assertEquals(viewModel.getFavorites().value?.size, 2)

            viewModel.handleQueryChange("F")
            assertEquals(viewModel.getFavorites().value?.first()?.name, "foo")
            assertEquals(viewModel.getFavorites().value?.size, 1)
        }
    }

    fun mockShowList() = listOf(
        Show(1L, "foo", listOf("foo", "bar"), 1, mockk(), mockk(), "Lorem", mockk()),
        Show(1L, "bar", listOf("foo", "bar"), 1, mockk(), mockk(), "Lorem", mockk())
    )

}