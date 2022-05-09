package com.gamezface.uicommon.extensions

import android.content.DialogInterface
import androidx.fragment.app.Fragment
import com.gamezface.domain.exception.AppNetworkException
import com.gamezface.domain.exception.RemoteException
import com.gamezface.uicommon.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Fragment.showErrorModal(
    throwable: Throwable?,
    action: DialogInterface.OnClickListener?
) {
    throwable?.printStackTrace()
    val message = when (throwable) {
        is AppNetworkException -> getString(R.string.network_error)
        is RemoteException -> getString(R.string.service_unavailable)
        else -> getString(R.string.network_error)
    }
    val dialog = MaterialAlertDialogBuilder(requireContext())
        .setTitle(getString(R.string.something_went_wrong))
        .setMessage(message)
        .setPositiveButton(getString(R.string.try_again), action)
        .setNeutralButton(this.getString(R.string.ok), null)
    dialog.show()
}
