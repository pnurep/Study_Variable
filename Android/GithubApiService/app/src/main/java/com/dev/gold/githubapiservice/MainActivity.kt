package com.dev.gold.githubapiservice

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.dev.gold.githubapiservice.databinding.ActivityMainBinding
import com.dev.gold.githubapiservice.repository.GithubServiceRepoImpl
import com.dev.gold.githubapiservice.viewmodel.MainViewModel
import com.dev.gold.githubapiservice.viewmodel.MainViewModelFactory

class MainActivity : BaseActivity() {

    private val listAdapter by lazy { ListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val viewModel =
            ViewModelProviders.of(this, MainViewModelFactory(GithubServiceRepoImpl())).get(MainViewModel::class.java)
        lifecycle.addObserver(viewModel)
        viewModel.stargazerLiveData.observe(this, Observer {
            it?.let { listAdapter.data = it }
        })

        with(binding.list) {
            setHasFixedSize(true)
        }

        binding.mainViewModel = viewModel
        binding.listAdapter = this.listAdapter

    }

}
