package com.gamezface.presentation.viewmodels.pin

import android.content.Context
import android.content.SharedPreferences
import com.gamezface.presentation.viewmodels.BaseViewModelTest
import junit.framework.TestCase.*
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import java.util.concurrent.atomic.AtomicReference

class PinViewModelTest : BaseViewModelTest() {
    private val viewModel by lazy {
        PinViewModel(encryptSharedPreferences)
    }

    @Before
    override fun setup() {
        super.setup()
        val sharedPrefs = Mockito.mock(SharedPreferences::class.java)
        val context: Context = Mockito.mock(Context::class.java)
        Mockito.`when`(
            context.getSharedPreferences(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyInt()
            )
        ).thenReturn(sharedPrefs)
        Mockito.`when`(encryptSharedPreferences.getSharedPreferences()).thenReturn(sharedPrefs)
        Mockito.`when`(
            encryptSharedPreferences.getSharedPreferences().getString(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString()
            )
        ).thenReturn("1234")
        Mockito.`when`(
            encryptSharedPreferences.getSharedPreferences().edit()
        ).thenAnswer {

        }
    }

    @Test
    fun pinCodeObserverTest() {
        val auth = AtomicReference<Boolean>().apply { set(false) }
        viewModel.getAuthentication().observe(lifecycleOwner) {
            auth.set(it)
        }

        assertNull(viewModel.getPin().value)
        viewModel.numPadListener.onNumberClicked('1')
        assertFalse(auth.get())

        assertNotNull(viewModel.getPin().value)
        assertEquals(viewModel.getPin().value, "1")

        viewModel.numPadListener.onNumberClicked('2')
        assertEquals(viewModel.getPin().value, "12")

        viewModel.numPadListener.onNumberClicked('3')
        assertEquals(viewModel.getPin().value, "123")

        viewModel.numPadListener.onNumberClicked('4')
        assertEquals(viewModel.getPin().value, "1234")
        assertTrue(auth.get())

        viewModel.numPadListener.onNumberClicked('5')
        assertEquals(viewModel.getPin().value, "1234")
    }
}