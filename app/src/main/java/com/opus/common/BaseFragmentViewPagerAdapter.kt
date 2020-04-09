package com.opus.common

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.opus.common.customs.TabLayoutItem

class BaseFragmentViewPagerAdapter(
    fragmentHost: Fragment,
    private val tabLayoutItems: List<TabLayoutItem>
) : FragmentStateAdapter(fragmentHost.childFragmentManager, fragmentHost.viewLifecycleOwner.lifecycle) {

    override fun getItemCount(): Int = tabLayoutItems.count()

    override fun createFragment(position: Int): Fragment = tabLayoutItems[position].fragmentInitializer()
}