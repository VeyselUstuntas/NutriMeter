package com.vustuntas.nutrimeter.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.vustuntas.nutrimeter.R

fun ImageView.downloadImage(url : String,placeHolder: CircularProgressDrawable) {
    val options = RequestOptions.placeholderOf(placeHolder).error(R.drawable.ic_launcher_background)
    Glide.with(context).setDefaultRequestOptions(options).load(url).into(this)
}

fun buildPlaceholder(context : Context) : CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}