package com.opus.common

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.opus.common.customs.TabLayoutItem
import com.opus.mobile.R
import com.opus.util.AutoClearedValue

abstract class BaseViewPagerFragment : BaseFragment() {

    private var adapter: BaseFragmentViewPagerAdapter by AutoClearedValue()
    private var tabLayoutMediator: TabLayoutMediator by AutoClearedValue()
    private var tabLayoutItems: List<TabLayoutItem> by AutoClearedValue()

    fun setupViewPager(
        viewPager: ViewPager2,
        tabLayout: TabLayout,
        tabs: List<TabLayoutItem>
    ) {
        tabLayoutItems = tabs

        adapter = BaseFragmentViewPagerAdapter(this, tabLayoutItems)

        viewPager.adapter = adapter

        tabLayoutMediator = TabLayoutMediator(tabLayout, viewPager, true) { tab, position ->
            val customTabItem = LayoutInflater
                .from(requireContext())
                .inflate(R.layout.item_custom_tab, null)
                .findViewById(R.id.tabText) as TextView

            customTabItem.text = tabs[position].title
            tab.customView = customTabItem
        }.apply {
            attach()
        }
    }

    fun onViewPagerDetached(view: ViewPager2) {
        view.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener{
            override fun onViewDetachedFromWindow(v: View?) {
                view.adapter = null
            }

            override fun onViewAttachedToWindow(v: View?) = Unit
        })
    }
}