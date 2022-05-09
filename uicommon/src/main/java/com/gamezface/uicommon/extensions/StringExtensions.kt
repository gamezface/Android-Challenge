package com.gamezface.uicommon.extensions

import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.text.Html
import android.text.Spanned

fun String.fromHtml(): Spanned? {
    return if (SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(this)
    }
}