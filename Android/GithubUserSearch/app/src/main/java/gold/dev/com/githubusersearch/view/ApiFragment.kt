package gold.dev.com.githubusersearch.view

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import gold.dev.com.githubusersearch.ListAdapter
import gold.dev.com.githubusersearch.R
import gold.dev.com.githubusersearch.databinding.FragmentApiBinding
import gold.dev.com.githubusersearch.db.SQLiteHelper


class ApiFragment : Fragment() {

    companion object {
        fun newInstance(): ApiFragment {
            return ApiFragment()
        }
    }

    private val viewModel by lazy { (activity as MainActivity).takeViewModel() }
    private val listAdapter by lazy { ListAdapter(viewModel) }
    private val db by lazy { SQLiteHelper(this.context!!, "api") }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //lifecycle.addObserver(viewModel)

        if (savedInstanceState != null) {
            //listAdapter.data = savedInstanceState.get("data") as ArrayList<Users.Item>
            val storedData = db.getStoredData()
            listAdapter.data = storedData
        }

        viewModel.userData.observe(this, Observer {
            it?.run {
                if (this.size > 0)
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

            // 이미 25.0.0 부터는 디폴트로 true 로 설정되어 있음 참고로 25.0.0 은 리사이클러뷰에 버그가 있으니
            // 25.0.1 부터 사용하는 것이 권장된다.
            // list.layoutManager.isItemPrefetchEnabled = true
            // 요건 중첩리스트 상황일 때만 사용하는 메서드! 중첩상황일때 이 메서드를 사용해 값을 지정해 놓으면 inner 리사이클러뷰가
            // 그 지정값만큼 이이템을 prefetch 하려고 시도한다. 내부 디폴트 값은 2.
            // layoutManager 가 중첩상황에 처해있지 않다면 이 메서드를 호출해도 아무것도 발생하지 않는다.
            // (list.layoutManager as LinearLayoutManager).initialPrefetchItemCount = 10
            DividerItemDecoration(list.context, LinearLayout.VERTICAL).apply {
                setDrawable(ContextCompat.getDrawable(list.context, R.drawable.list_divider)!!)
                list.addItemDecoration(this)
            }
        }

        return binding.root
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //outState.putSerializable("data", listAdapter.data)
        outState.putBoolean("isStored", true)
    }

    override fun onPause() {
        super.onPause()
        db.deleteData()
        db.saveData(listAdapter.data)
    }

    override fun onDestroy() {
        db.close()
        super.onDestroy()
    }

}