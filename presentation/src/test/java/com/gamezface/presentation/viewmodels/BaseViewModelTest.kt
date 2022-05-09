package com.gamezface.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.gamezface.domain.cast.repository.CastDetailsRepository
import com.gamezface.domain.pin.EncryptSharedPreferences
import com.gamezface.domain.show.repository.ShowRepository
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations


@Ignore("Base class for viewModel testing")
abstract class BaseViewModelTest {

    @Before
    open fun setup() {
        mockLifecycleOwner()
        MockitoAnnotations.openMocks(this)
    }

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @get:Rule
    val testRule = InstantTaskExecutorRule()

    @Mock
    lateinit var encryptSharedPreferences: EncryptSharedPreferences

    @Mock
    lateinit var castDetailsRepository: CastDetailsRepository

    @Mock
    lateinit var showRepository: ShowRepository

    protected val lifecycleOwner: LifecycleOwner = mock(LifecycleOwner::class.java)

    protected fun mockLifecycleOwner() {
        val lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(lifecycleOwner)
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        Mockito.`when`(lifecycleOwner.lifecycle).thenReturn(lifecycleRegistry)
    }
}