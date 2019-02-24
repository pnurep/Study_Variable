package gold.dev.com.githubusersearch.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.os.Bundle
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import gold.dev.com.githubusersearch.ListAdapter
import gold.dev.com.githubusersearch.R
import gold.dev.com.githubusersearch.databinding.ActivityMainBinding
import gold.dev.com.githubusersearch.databinding.FragmentApiBinding
import gold.dev.com.githubusersearch.databinding.FragmentLocalBinding
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

    companion object {
        private const val TAB_COUNT = 2
    }

    private val mPagerAdapter by lazy { PagerAdapter(supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.viewModel = viewModel

        setSupportActionBar(toolbar)

        with(viewPager) {
            adapter = mPagerAdapter
            addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
            tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(this))
        }

    }

    fun takeViewModel(): MainViewModel {
        return viewModel
    }

    inner class PagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int): Fragment = when (position) {
            0 -> ApiFragment.newInstance()
            else -> LocalFragment.newInstance()
        }

        override fun getCount(): Int {
            return TAB_COUNT
        }
    }

    class ApiFragment : Fragment() {

        private val viewModel by lazy { (activity as MainActivity).takeViewModel() }
        private val listAdapter by lazy { ListAdapter(viewModel) }

        override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)
            lifecycle.addObserver(viewModel)
            viewModel.userData.observe(this, Observer {
                it?.run {
                    listAdapter.data = this
                } ?: run {
                    listAdapter.data = ArrayList()
                }
            })
        }

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val binding = DataBindingUtil.inflate<FragmentApiBinding>(
                inflater,
                R.layout.fragment_api,
                container,
                false
            )

            with(binding) {
                viewModel = this@ApiFragment.viewModel
                listAdapter = this@ApiFragment.listAdapter
                list.setHasFixedSize(true)
                list.layoutManager.isItemPrefetchEnabled
                DividerItemDecoration(list.context, LinearLayout.VERTICAL).apply {
                    setDrawable(ContextCompat.getDrawable(list.context, R.drawable.list_divider)!!)
                    list.addItemDecoration(this)
                }
            }

            return binding.root
        }

        companion object {
            fun newInstance(): ApiFragment {
                return ApiFragment()
            }
        }
    }

    class LocalFragment : Fragment() {

        private val viewModel by lazy { (activity as MainActivity).takeViewModel() }
        private val listAdapter by lazy { ListAdapter(viewModel) }

        override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)
            lifecycle.addObserver(viewModel)
            viewModel.localLikeData.observe(this, Observer {
                it?.run {
                    listAdapter.data = it
                }
            })
        }

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val binding = DataBindingUtil.inflate<FragmentLocalBinding>(
                inflater,
                R.layout.fragment_local,
                container,
                false
            )

            with(binding) {
                viewModel = this@LocalFragment.viewModel
                listAdapter = this@LocalFragment.listAdapter
                list.setHasFixedSize(true)
                list.layoutManager.isItemPrefetchEnabled
                DividerItemDecoration(list.context, LinearLayout.VERTICAL).apply {
                    setDrawable(ContextCompat.getDrawable(list.context, R.drawable.list_divider)!!)
                    list.addItemDecoration(this)
                }
            }

            return binding.root
        }

        companion object {
            fun newInstance(): LocalFragment {
                return LocalFragment()
            }
        }

    }
}
