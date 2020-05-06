package com.opus.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.Hold
import com.opus.common.BaseFragment
import com.opus.common.SHARED_ELEMENT
import com.opus.mobile.R
import kotlinx.android.synthetic.main.fragment_order.order_fab

class HomeFragment : BaseFragment() {

    private val hold = Hold()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.fragment_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        exitTransition = hold

        order_fab.setOnClickListener {
            val extras = FragmentNavigatorExtras(order_fab to SHARED_ELEMENT)
            findNavController().navigate(
                R.id.action_orderFragment_to_newOrderFragment,
                null,
                null,
                extras
            )
        }
    }
}
