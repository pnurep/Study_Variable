package gold.dev.com.githubusersearch.viewmodel

import android.app.Application
import android.arch.lifecycle.*
import android.databinding.ObservableField
import android.support.v7.widget.SearchView
import android.view.View
import android.widget.Toast
import gold.dev.com.githubusersearch.ListLiveData
import gold.dev.com.githubusersearch.model.Users
import gold.dev.com.githubusersearch.repository.GithubUserSearchRepo
import gold.dev.com.githubusersearch.view.CheckableImageView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class MainViewModel(
    private val app: Application,
    private val repo: GithubUserSearchRepo
) : ViewModel() {

    val isLoading = ObservableField<Boolean>(false)

    val userData = ListLiveData<Users.Item>()

    val localLikeData = ListLiveData<Users.Item>()

    private val disposable = CompositeDisposable()

    val onQueryTextListener = object : SearchView.OnQueryTextListener {

        override fun onQueryTextSubmit(query: String?): Boolean {
            query?.let { getUserSearchInfo(it) }
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            if (newText?.isBlank() == true)
                userData.value = null

            return false
        }

    }

    fun getUserSearchInfo(name: String) {

        if (isLoading.get()!!)
            return

        disposable.clear()

        val userList = ArrayList<Users.Item>()

        repo.getUserSearchInfo(name)
            .doOnSubscribe { isLoading.set(true) }
            .subscribeOn(Schedulers.io())
            .flatMap { Observable.fromIterable(it.items) }
            .doFinally { isLoading.set(false) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    userList.add(confirmLike(it))
                },
                onError = {
                    Toast.makeText(app, it.message, Toast.LENGTH_SHORT).show()
                },
                onComplete = {
                    userData.value = userList
                }
            ).addTo(disposable)
    }

    private fun confirmLike(input: Users.Item): Users.Item {
        if (localLikeData.contains(input))
            input.like.set(true)

        return input
    }

    fun setLike(view: View, user: Users.Item) {
        if (view is CheckableImageView) {
            when (user.like.get()) {
                true -> {
                    val user_got = userData.get(user)
                    user_got.like.set(false)
                    user_got.like.notifyChange()
                    localLikeData.remove(user)
                }

                false -> {
                    val user_got = userData.get(user)
                    user_got.like.set(true)
                    user_got.like.notifyChange()
                    localLikeData.add(user)
                }

            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}