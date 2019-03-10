package com.dev.gold.gsonexample

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import com.dev.gold.gsonexample.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


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
        //return Gson().fromJson<List<User>>(str, object : TypeToken<List<User>>() {}.type)
    }

//    inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object: TypeToken<T>() {}.type)
//    and then you can call it in this way:
//    val turns = Gson().fromJson<Turns>(pref.turns)
//    // or
//    val turns: Turns = Gson().fromJson(pref.turns)
}
