package com.tgyuu.presentation.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapter {
    @BindingAdapter("glide")
    @JvmStatic
    fun setGlide(imageView: ImageView, uri: String) {
        Glide.with(imageView.context).load(uri).circleCrop().into(imageView)
    }

}