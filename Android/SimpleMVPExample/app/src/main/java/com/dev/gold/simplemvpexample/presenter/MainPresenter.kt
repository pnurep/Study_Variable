package com.dev.gold.simplemvpexample.presenter

import com.dev.gold.simplemvpexample.contract.MainContract

class MainPresenter : MainContract.IPresenter {
    private lateinit var view: MainContract.IView

    override fun attachView(view: MainContract.IView) {
        this.view = view
    }

    override fun task1() {
        view.setTaskText("clicked!")
    }

}
