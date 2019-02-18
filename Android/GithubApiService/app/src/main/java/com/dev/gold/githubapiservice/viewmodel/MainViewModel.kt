package com.dev.gold.githubapiservice.viewmodel

import android.arch.lifecycle.*
import android.databinding.ObservableField
import com.dev.gold.githubapiservice.model.Stargazer
import com.dev.gold.githubapiservice.repository.GithubServiceRepoImpl
import com.dev.gold.githubapiservice.repository.GithubServiceRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.atomic.AtomicInteger

class MainViewModel(private val githubServiceRepository: GithubServiceRepository) : ViewModel(), LifecycleObserver {

    val isLoading = ObservableField<Boolean>(false)

    val stargazerLiveData = MutableLiveData<List<Stargazer>>()

    private val stargazerPage = AtomicInteger(1)

    private val disposable = CompositeDisposable()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun getGithubRepoStargazers() {
        githubServiceRepository.getGithubRepo()
                .doOnSubscribe { isLoading.set(true) }
                .subscribeOn(Schedulers.io())
                .flatMap {
                    githubServiceRepository.getStargazers(stargazerPage.get()).also { stargazerPage.getAndAdd(1) }
                }.doFinally {
                    isLoading.set(false)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = {
                            stargazerLiveData.postValue(it)
                        },
                        onError = {

                        },
                        onComplete = {

                        }
                ).addTo(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}