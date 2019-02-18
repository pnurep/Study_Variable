package com.dev.gold.githubapiservice

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.dev.gold.githubapiservice.databinding.ListItemBinding
import com.dev.gold.githubapiservice.model.Stargazer

class ListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data: List<Stargazer> = listOf()
        set(value) {
            field += value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ListItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.list_item,
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ListAdapter.ViewHolder -> {
                val stargazer = data.getOrNull(position) ?: return
                holder.binding.setVariable(BR.stargazer, stargazer)
                holder.binding.executePendingBindings()
            }
        }
    }

    inner class ViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)
}