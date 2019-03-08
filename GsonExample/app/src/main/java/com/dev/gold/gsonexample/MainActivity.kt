package com.dev.gold.gsonexample

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import com.dev.gold.gsonexample.model.User
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {

    val listAdapter by lazy { ListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(list) {
            this.adapter = listAdapter
            this.layoutManager = LinearLayoutManager(this@MainActivity)
        }

        loadData()
    }

    @SuppressLint("StaticFieldLeak")
    fun loadData() {
        object : AsyncTask<Void, Void, String>() {
            override fun doInBackground(vararg params: Void?): String {
                return Remote.getData("https://api.github.com/users")
            }

            override fun onPostExecute(result: String?) {
                result?.let {
                    listAdapter.data = parseData(it)
                }
            }
        }.execute()
    }

    fun parseData(str: String): List<User> {
        return Gson().fromJson(str, Array<User>::class.java).toList()
    }

}
