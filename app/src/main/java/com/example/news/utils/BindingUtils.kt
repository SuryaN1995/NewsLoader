package com.example.news.utils

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.news.R

@BindingAdapter("bind:loadUrl")
fun loadImage(view: AppCompatImageView, url: String) {
    val context = view.context
    Glide.with(context).load(url)
        .thumbnail(0.05f)
        .transition(DrawableTransitionOptions.withCrossFade())
        .transform(CenterCrop())
        .apply(RequestOptions().override(125, 125))
        .placeholder(R.drawable.ic_placeholder_thumb)
        .into(view)
}

inline fun <VM : ViewModel?> viewModelFactory(crossinline f: () -> VM) =
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(aClass: Class<T>): T = f() as T
    }