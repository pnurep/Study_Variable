package com.android.gold.kotlinstudy

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

/**
 * Created by Gold on 2017. 11. 15..
 */
class Adapter(val context: Context, val items: List<NationData>) : RecyclerView.Adapter<ViewHolder>()
{

	private var onItemClick: View.OnClickListener? = null

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.img_flag.setImageResource(R.drawable.ic_launcher_foreground)
		holder.txt_name.setText("")
	}

	override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder? {
		val mainView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
		return ViewHolder(mainView)
	}

	override fun getItemCount(): Int {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
	val img_flag = view.findViewById<View>(R.id.img_flag) as ImageView
	val txt_name = view.findViewById<View>(R.id.text_name) as TextView
	val txt_capital = view.findViewById<View>(R.id.capital) as TextView
}

data class NationData (
		var resId:Int,
		var name: String,
		var capital: String
)