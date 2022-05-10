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

class CastDetailsViewModelTest : BaseViewModelTest() {

    @Captor
    lateinit var longCaptor: ArgumentCaptor<Long>

    private val viewModel by lazy {
        CastDetailsViewModel(castDetailsRepository)
    }

    @Before
    override fun setup() {
        super.setup()
        runBlocking {
            Mockito.`when`(castDetailsRepository.getCastDetails(any())).thenReturn(mockk())
        }
    }

    @Test
    fun loadCastDetailsTest() {
        assertNull(viewModel.getCastDetails().value)
        assertEquals(viewModel.id, -1L)

        runBlocking {
            viewModel.loadDetails()
            verify(castDetailsRepository, times(0)).getCastDetails(any())
            assertEquals(viewModel.id, -1L)

            viewModel.id = 1L
            viewModel.loadDetails()
            verify(castDetailsRepository, times(1)).getCastDetails(capture(longCaptor))
            assertEquals(longCaptor.value, 1L)
            assertNotNull(viewModel.getCastDetails().value)

        }
    }

}