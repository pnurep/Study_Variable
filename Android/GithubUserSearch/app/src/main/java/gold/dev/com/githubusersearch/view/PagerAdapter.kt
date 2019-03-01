package gold.dev.com.githubusersearch.view

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class PagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    companion object {
        private const val TAB_COUNT = 2
    }

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> ApiFragment.newInstance()
        else -> LocalFragment.newInstance()
    }

    override fun getCount(): Int {
        return TAB_COUNT
    }
}