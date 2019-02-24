package com.dev.gold.githubapiservice.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.dev.gold.githubapiservice.ListAdapter
import com.dev.gold.githubapiservice.R
import com.dev.gold.githubapiservice.databinding.ActivityMainBinding
import com.dev.gold.githubapiservice.repository.GithubServiceRepoImpl
import com.dev.gold.githubapiservice.viewmodel.MainViewModel
import com.dev.gold.githubapiservice.viewmodel.MainViewModelFactory

class MainActivity : BaseActivity() {

    private lateinit var listAdapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val viewModel =
            ViewModelProviders.of(this, MainViewModelFactory(application, GithubServiceRepoImpl()))
                .get(MainViewModel::class.java)
                .also {
                    listAdapter = ListAdapter(it)
                }
        lifecycle.addObserver(viewModel)
        viewModel.stargazerLiveData.observe(this,
            Observer {
                it?.run {
                    listAdapter.data = this
                } ?: run { listAdapter.data = listOf() }
            })

        with(binding) {
            list.setHasFixedSize(true)
            mainViewModel = viewModel
            this.listAdapter = this@MainActivity.listAdapter
        }

    }

}
