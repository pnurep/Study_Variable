package com.dev.gold.githubapiservice

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.dev.gold.githubapiservice.databinding.ListItemBinding
import com.dev.gold.githubapiservice.databinding.ListItemLoaderBinding
import com.dev.gold.githubapiservice.model.Stargazer
import com.dev.gold.githubapiservice.viewmodel.MainViewModel


class ListAdapter(private val mainViewModel: MainViewModel) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    companion object {

        private const val VIEW_HOLDER_TYPE_DEFAULT = 1
        private const val VIEW_HOLDER_TYPE_LOADER = VIEW_HOLDER_TYPE_DEFAULT + 1

    }

    var data: List<Stargazer> = listOf()
        set(value) {
            if (value.isEmpty() || data.isNotEmpty()) {
                if (field[data.size - 1].login == "LOADER")
                    field = field.dropLast(1)
            }

            field += value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.ViewHolder = when (viewType) {
        VIEW_HOLDER_TYPE_LOADER -> {
            val binding = DataBindingUtil.inflate<ListItemLoaderBinding>(
                LayoutInflater.from(parent.context),
                R.layout.list_item_loader,
                parent,
                false
            )

            ViewHolder.LoaderViewHolder(binding)
        }
        else -> {
            val binding = DataBindingUtil.inflate<ListItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.list_item,
                parent,
                false
            )

            ViewHolder.DefaultViewHolder(binding)
        }
    }

    override fun getItemViewType(position: Int) = when {
        isLoaderPosition(position) -> VIEW_HOLDER_TYPE_LOADER
        else -> VIEW_HOLDER_TYPE_DEFAULT
    }

    private fun isLoaderPosition(position: Int): Boolean {
        return data[position].login == "LOADER"
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ListAdapter.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder.DefaultViewHolder -> {
                val stargazer = data.getOrNull(position) ?: return
                holder.binding.setVariable(BR.stargazer, stargazer)
                holder.binding.executePendingBindings()
            }
            is ViewHolder.LoaderViewHolder -> {
                holder.binding.setVariable(BR.mainViewModel, mainViewModel)
                holder.binding.executePendingBindings()
            }
        }
    }

    sealed class ViewHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

        class DefaultViewHolder(val binding: ListItemBinding) : ViewHolder(binding)

        class LoaderViewHolder(val binding: ListItemLoaderBinding) : ViewHolder(binding)

    }

}