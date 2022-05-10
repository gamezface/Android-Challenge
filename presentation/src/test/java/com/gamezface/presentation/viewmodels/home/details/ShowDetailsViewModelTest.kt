package com.gamezface.presentation.viewmodels.home

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

class ShowDetailsViewModelTest : BaseViewModelTest() {

    @Captor
    lateinit var intCaptor: ArgumentCaptor<Int>

    private val viewModel by lazy {
        ShowDetailsViewModel(showRepository)
    }

    @Before
    override fun setup() {
        super.setup()
        runBlocking {
            Mockito.`when`(showRepository.getShows(anyInt())).thenReturn(listOf(mockk()))
        }
    }

    @Test
    fun loadShowsTest() {
        assertNull(viewModel.getShows().value)

        runBlocking {
            viewModel.loadShows()
            verify(showRepository, times(1)).getShows(capture(intCaptor))
            assertEquals(intCaptor.value, 0)
            viewModel.handleLoadShowsSuccess(listOf())

            assertNotNull(viewModel.getShows().value)
            assertFalse(viewModel.getShows().value?.data.isNullOrEmpty())

            viewModel.loadShows()
            verify(showRepository, times(2)).getShows(capture(intCaptor))
            assertNotNull(viewModel.getShows().value)
            assertFalse(viewModel.getShows().value?.data.isNullOrEmpty())
            assertEquals(intCaptor.value, 1)

            viewModel.handleEndOfPages()
            viewModel.loadShows()
            verify(showRepository, times(2)).getShows(capture(intCaptor))
        }
    }

}