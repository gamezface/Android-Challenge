package com.gamezface.domain.pin

interface NumPadListener {
    fun onNumberClicked(number: Char)
    fun onEraseClicked()
}