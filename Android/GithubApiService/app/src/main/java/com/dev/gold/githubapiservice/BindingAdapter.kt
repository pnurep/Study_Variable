package com.dev.gold.githubapiservice

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
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

        @JvmStatic
        @BindingAdapter("app:addScrollListener")
        fun addScrollListener(list: RecyclerView, listener: RecyclerView.OnScrollListener) {
            list.addOnScrollListener(listener)
        }

    }

}