package com.gamezface.presentation.viewmodels.home

import com.gamezface.presentation.viewmodels.BaseViewModelTest
import io.mockk.mockk
import junit.framework.TestCase.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Captor
import org.mockito.Mockito
import org.mockito.kotlin.capture
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class SearchViewModelTest : BaseViewModelTest() {

    @Captor
    lateinit var stringCaptor: ArgumentCaptor<String>

    private val viewModel by lazy {
        SearchViewModel(showRepository)
    }

    @Before
    override fun setup() {
        super.setup()
        runBlocking {
            Mockito.`when`(showRepository.searchPeople(anyString())).thenReturn(listOf(mockk()))
            Mockito.`when`(showRepository.searchShows(anyString())).thenReturn(listOf(mockk()))
        }
    }

    @Test
    fun searchTest() {
        assertNull(viewModel.getSearchResult().value)

        viewModel.handleQueryChange("John")
        runBlocking {
            viewModel.searchPeople()
            verify(showRepository, times(1)).searchPeople(capture(stringCaptor))
            assertEquals(stringCaptor.value, "John")

            assertNotNull(viewModel.getSearchResult().value)
            assertFalse(viewModel.getSearchResult().value?.data.isNullOrEmpty())

            viewModel.searchShows()
            verify(showRepository, times(1)).searchShows(capture(stringCaptor))
            assertNotNull(viewModel.getSearchResult().value)
            assertFalse(viewModel.getSearchResult().value?.data.isNullOrEmpty())
        }
    }

}