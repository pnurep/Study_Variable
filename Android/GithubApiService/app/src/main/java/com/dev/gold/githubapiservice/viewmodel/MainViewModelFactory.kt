package com.dev.gold.githubapiservice.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.dev.gold.githubapiservice.repository.GithubServiceRepository

class MainViewModelFactory(private val githubServiceRepository: GithubServiceRepository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(githubServiceRepository) as T
    }

}