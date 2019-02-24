package gold.dev.com.githubusersearch

import android.databinding.BindingAdapter
import android.databinding.ObservableField
import android.support.v7.widget.SearchView
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import gold.dev.com.githubusersearch.view.CheckableImageView

class BindingAdapter {

    companion object {

        @JvmStatic
        @BindingAdapter("app:loadImageFromUrl")
        fun loadImageFromUrl(view: ImageView, url: String) {
            Glide.with(view)
                .setDefaultRequestOptions(
                    RequestOptions().apply {
                        placeholder(R.drawable.ic_launcher_foreground)
                        error(R.drawable.ic_launcher_foreground)
                        diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    }
                )
                .load(url)
                .into(view)
        }

        @JvmStatic
        @BindingAdapter("app:setLike")
        fun setLike(view: ImageView, like: ObservableField<Boolean>) {
            if (view is CheckableImageView) {
                when (like.get()) {
                    true -> view.isChecked = true
                    false -> view.isChecked = false
                }
            }
        }

        @JvmStatic
        @BindingAdapter("app:addOnQueryTextListener")
        fun addOnQueryTextListener(view: SearchView, listener: SearchView.OnQueryTextListener) {
            view.setOnQueryTextListener(listener)
        }

    }

}