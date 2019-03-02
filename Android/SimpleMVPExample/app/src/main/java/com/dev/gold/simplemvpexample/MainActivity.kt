package com.dev.gold.simplemvpexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.dev.gold.simplemvpexample.contract.MainContract
import com.dev.gold.simplemvpexample.presenter.MainPresenter
import com.dev.gold.simplemvpexample.view.MainView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view: MainContract.IView = MainView(this)
        setContentView(view.getView())

        val presenter: MainContract.IPresenter = MainPresenter()
        presenter.attachView(view)

        view.setPresenter(presenter)

    }

}
