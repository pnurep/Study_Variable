package gold.dev.com.githubusersearch

import android.arch.lifecycle.MutableLiveData

class ListLiveData<T> : MutableLiveData<ArrayList<T>>() {

    init {
        value = ArrayList()
    }

    fun replace(oldItem: T, newItem: T) {
        val items = value

        items?.let {
            val index = it.indexOf(oldItem)
            if (index != -1)
                it[index] = newItem
        }

        value = items
    }

    fun get(item: T): T {
        val items = value
        return items!![items.indexOf(item)]
    }

    fun contains(item: T): Boolean {
        val items = value
        return items!!.contains(item)
    }

    fun add(item: T) {
        val items = value
        items!!.add(item)
        value = items
    }

    fun remove(item: T) {
        val items = value
        items!!.remove(item)
        value = items
    }

    fun notifyChange() {
        val items = value
        value = items
    }

}