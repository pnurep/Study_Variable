package gold.dev.com.githubusersearch.view

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import gold.dev.com.githubusersearch.ListAdapter
import gold.dev.com.githubusersearch.R
import gold.dev.com.githubusersearch.databinding.FragmentLocalBinding

class LocalFragment : Fragment() {

    companion object {
        fun newInstance(): LocalFragment {
            return LocalFragment()
        }
    }

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
            list.layoutManager.isItemPrefetchEnabled = true
            (list.layoutManager as LinearLayoutManager).initialPrefetchItemCount = 10
            DividerItemDecoration(list.context, LinearLayout.VERTICAL).apply {
                setDrawable(ContextCompat.getDrawable(list.context, R.drawable.list_divider)!!)
                list.addItemDecoration(this)
            }
        }

        return binding.root
    }

}