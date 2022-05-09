package com.gamezface.uicommon.progress

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.gamezface.uicommon.R

class ProgressDialog(private val context: Context) {
    private val dialog by lazy {
        Dialog(context)
    }

    init {
        dialog.setContentView(R.layout.progress_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.create()
    }

    fun showProgress(cancelable: Boolean = false) {
        dialog.setCancelable(cancelable)
        dialog.show()
    }

    fun hideProgress() {
        dialog.dismiss()
    }
}