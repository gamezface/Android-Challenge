package com.gamezface.presentation.viewmodels.home.details

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
import org.mockito.kotlin.any
import org.mockito.kotlin.capture
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class ShowDetailsViewModelTest : BaseViewModelTest() {

    @Captor
    lateinit var longCaptor: ArgumentCaptor<Long>

    private val viewModel by lazy {
        ShowDetailsViewModel(showRepository)
    }

    @Before
    override fun setup() {
        super.setup()
        runBlocking {
            Mockito.`when`(showRepository.getDetails(any())).thenReturn(mockk())
        }
    }

    @Test
    fun loadShowsTest() {
        assertNull(viewModel.getShowDetails().value)

        runBlocking {
            viewModel.loadDetails()
            verify(showRepository, times(0)).getDetails(any())
            assertEquals(viewModel.id, -1L)
            assertNull(viewModel.getFavorite().value)

            viewModel.handleFavoriteCheck(true)
            verify(showRepository, times(0)).insertFavorite(any())
            assertTrue(viewModel.getFavorite().value == true)

            viewModel.handleFavoriteCheck(false)
            verify(showRepository, times(0)).removeFavorite(any())
            assertTrue(viewModel.getFavorite().value == false)

            viewModel.id = 1L
            viewModel.loadDetails()
            verify(showRepository, times(1)).getDetails(capture(longCaptor))
            assertEquals(longCaptor.value, 1L)
            assertNotNull(viewModel.getShowDetails().value)

            viewModel.handleFavoriteCheck(true)
            verify(showRepository, times(1)).insertFavorite(any())
            assertTrue(viewModel.getFavorite().value == true)

            viewModel.handleFavoriteCheck(false)
            verify(showRepository, times(1)).removeFavorite(any())
            assertTrue(viewModel.getFavorite().value == false)
        }
    }

}