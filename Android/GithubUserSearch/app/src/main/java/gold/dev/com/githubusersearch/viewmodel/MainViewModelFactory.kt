package gold.dev.com.githubusersearch.viewmodel

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import gold.dev.com.githubusersearch.repository.GithubUserSearchRepo
import gold.dev.com.githubusersearch.repository.GithubUserSearchRepoImpl

class MainViewModelFactory(
    private val app: Application,
    private val repo: GithubUserSearchRepo
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(app, repo as GithubUserSearchRepoImpl) as T
    }

}