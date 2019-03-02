package com.dev.gold.simplemvpexample.view

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.dev.gold.simplemvpexample.R
import com.dev.gold.simplemvpexample.contract.MainContract

class MainView(context: Context) : MainContract.IView, View.OnClickListener {

    private var rootView: View = LayoutInflater
        .from(context).inflate(R.layout.activity_main, null)

    private val textView: TextView
    private val btn: Button

    private lateinit var presenter: MainContract.IPresenter

    init {
        textView = rootView.findViewById(R.id.tv_1)
        btn = rootView.findViewById(R.id.btn_1)
        btn.setOnClickListener(this@MainView)
    }

    override fun setPresenter(presenter: MainContract.IPresenter) {
        this.presenter = presenter
    }

    override fun getView(): View {
        return rootView
    }

    override fun setTaskText(str: String) {
        textView.text = str
    }

    override fun onClick(v: View?) = when (v?.id) {
        R.id.btn_1 -> presenter.task1()
        else -> Unit
    }

}
