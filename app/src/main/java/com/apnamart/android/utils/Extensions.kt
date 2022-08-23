package com.apnamart.android.utils

import android.content.Context
import android.widget.ImageView
import com.apnamart.android.R
import com.bumptech.glide.Glide
import java.io.File

fun ImageView.loadImage(context: Context, url: String) {
    Glide.with(context)
        .load(url)
        .circleCrop()
        .placeholder(R.drawable.round_rect_bg)
        .error(R.drawable.round_rect_bg)
        .into(this)
}

fun Context.clearCache() {
    try {
        val dir: File = cacheDir
        deleteDir(dir)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun deleteDir(dir: File?): Boolean {
    return if (dir != null && dir.isDirectory) {
        val children = dir.list()
        for (i in children.indices) {
            val success = deleteDir(File(dir, children[i]))
            if (!success) {
                return false
            }
        }
        dir.delete()
    } else if (dir != null && dir.isFile) {
        dir.delete()
    } else {
        false
    }
}