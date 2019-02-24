package gold.dev.com.githubusersearch

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import gold.dev.com.githubusersearch.databinding.ListItemBinding
import gold.dev.com.githubusersearch.model.Users
import gold.dev.com.githubusersearch.viewmodel.MainViewModel

class ListAdapter(private val mainViewModel: MainViewModel) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    var data: ArrayList<Users.Item> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.ViewHolder {
        val binding = DataBindingUtil.inflate<ListItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.list_item,
            parent,
            false
        )

        return ViewHolder.DefaultViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ListAdapter.ViewHolder, position: Int) = when (holder) {
        is ViewHolder.DefaultViewHolder -> {
            val user = data.getOrNull(position) ?: run { return }
            holder.binding.user = user
            holder.binding.viewModel = mainViewModel
            holder.binding.executePendingBindings()
        }
    }

    sealed class ViewHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

        class DefaultViewHolder(val binding: ListItemBinding) : ViewHolder(binding)

    }

}