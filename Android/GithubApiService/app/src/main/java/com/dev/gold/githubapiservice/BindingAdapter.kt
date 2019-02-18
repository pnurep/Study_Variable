package com.dev.gold.githubapiservice

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide

class BindingAdapter {

    companion object {
        @JvmStatic
        @BindingAdapter("app:loadImageFromUrl")
        fun loadImageFromUrl(view: ImageView, url: String) {
            Glide.with(view)
                .load(url)
                .into(view)
        }
    }

}