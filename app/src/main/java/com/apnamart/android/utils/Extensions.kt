package com.apnamart.android.utils

import android.content.Context
import android.widget.ImageView
import com.apnamart.android.R
import com.bumptech.glide.Glide

fun ImageView.loadImage(context: Context, url: String) {
    Glide.with(context)
        .load(url)
        .circleCrop()
        .placeholder(R.drawable.round_rect_bg)
        .error(R.drawable.round_rect_bg)
        .into(this)
}