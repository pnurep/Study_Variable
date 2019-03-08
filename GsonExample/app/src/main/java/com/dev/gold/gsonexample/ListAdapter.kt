package com.dev.gold.gsonexample

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.dev.gold.gsonexample.model.User

class ListAdapter : RecyclerView.Adapter<ListAdapter.Holder>() {

    var data: List<User> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.list_item, p0, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val user = data[position]
        holder.textId.text = "${user.id}"
        holder.textLogin.text = "${user.login}"
        Glide.with(holder.imageView.context)
            .load(user.avatar_url)
            .into(holder.imageView)
    }

    inner class Holder(val view: View) : RecyclerView.ViewHolder(view) {

        val imageView = view.findViewById<ImageView>(R.id.iv)
        val textId = view.findViewById<TextView>(R.id.textId)
        val textLogin = view.findViewById<TextView>(R.id.textLogin)

    }

}