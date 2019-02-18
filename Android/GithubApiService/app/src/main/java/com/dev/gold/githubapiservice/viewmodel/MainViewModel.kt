package com.dev.gold.githubapiservice.viewmodel

import android.app.Application
import android.arch.lifecycle.*
import android.databinding.ObservableField
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.dev.gold.githubapiservice.model.Stargazer
import com.dev.gold.githubapiservice.repository.GithubServiceRepoImpl
import com.dev.gold.githubapiservice.repository.GithubServiceRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

class MainViewModel(
    private val app: Application,
    private val githubServiceRepository: GithubServiceRepository
) : AndroidViewModel(app), LifecycleObserver {

    val isLoading = ObservableField<Boolean>(false)

    val stargazerLiveData = MutableLiveData<List<Stargazer>>()

    private val stargazerPage = AtomicInteger(1)

    private val disposable = CompositeDisposable()

    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            recyclerView?.let {
                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

                if (lastVisibleItemPosition != 0 &&
                    lastVisibleItemPosition == recyclerView.adapter!!.itemCount - 1) {

                    getGithubRepoStargazers()
                }
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun getGithubRepoStargazers() {

        if (isLoading.get()!!)
            return

        githubServiceRepository.getGithubRepo()
                .doOnSubscribe { isLoading.set(true) }
                .subscribeOn(Schedulers.io())
                .flatMap {
                    githubServiceRepository.getStargazers(stargazerPage.get())
                }.doFinally {
                    isLoading.set(false)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = {
                            postValue(makeLoader(it)).also { stargazerPage.getAndAdd(1) }
                        },
                        onError = {
                            Toast.makeText(app.applicationContext, it.message, Toast.LENGTH_SHORT).show()
                            postValue(null)
                        },
                        onComplete = {

                        }
                ).addTo(disposable)
    }

    private fun postValue(input: List<Stargazer>?) {
        if (input.isNullOrEmpty())
            stargazerLiveData.postValue(null)
        else
            stargazerLiveData.postValue(input)
    }

    private fun makeLoader(input: List<Stargazer>): List<Stargazer> {
        return input.plus(listOf(Stargazer(login = "LOADER")))
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}