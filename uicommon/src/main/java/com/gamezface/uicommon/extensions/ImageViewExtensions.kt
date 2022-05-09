package com.gamezface.uicommon.extensions

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gamezface.uicommon.R

fun ImageView.loadImage(view: View?, url: String?) {
    view?.let {
        Glide.with(view)
            .load(url)
            .apply(RequestOptions().placeholder(R.drawable.ic_image_search))
            .into(this)
    }
}