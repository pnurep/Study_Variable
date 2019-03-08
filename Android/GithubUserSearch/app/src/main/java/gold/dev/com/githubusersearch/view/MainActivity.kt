package gold.dev.com.githubusersearch.view

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import gold.dev.com.githubusersearch.R
import gold.dev.com.githubusersearch.databinding.ActivityMainBinding
import gold.dev.com.githubusersearch.repository.GithubUserSearchRepoImpl
import gold.dev.com.githubusersearch.viewmodel.MainViewModel
import gold.dev.com.githubusersearch.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProviders.of(
            this,
            MainViewModelFactory(application, GithubUserSearchRepoImpl)
        ).get(MainViewModel::class.java)
    }

    private val mPagerAdapter by lazy { PagerAdapter(supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.viewModel = viewModel

        setSupportActionBar(toolbar)

        with(viewPager) {
            adapter = mPagerAdapter
            // ViewPager 의 페이지가 넘어갈 때 이를 TabLayout 에 알려 동기를 맞춘다.
            addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
            // 탭의 선택상태를 제공한다. TabLayoutOnPageChangeListener 는 내부에 이미 필수 콜백들을 정의해 놓았다.
            tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(this))
        }
    }

    fun takeViewModel(): MainViewModel {
        return viewModel
    }

}
