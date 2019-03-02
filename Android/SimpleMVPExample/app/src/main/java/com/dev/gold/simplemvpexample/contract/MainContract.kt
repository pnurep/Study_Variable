package com.dev.gold.simplemvpexample.contract

import android.view.View

interface MainContract {

    interface IView {

        fun setPresenter(presenter: IPresenter)

        fun getView(): View

        fun setTaskText(str: String)

    }

    interface IPresenter {

        fun attachView(view: IView)

        fun task1()

    }

}