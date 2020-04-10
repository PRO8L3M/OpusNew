package com.opus.ui.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opus.mobile.R
import kotlinx.android.synthetic.main.fragment_order.order_create_order_button
import kotlinx.android.synthetic.main.fragment_order.order_gender_edit_text
import kotlinx.android.synthetic.main.fragment_order.order_height_edit_text
import kotlinx.android.synthetic.main.fragment_order.order_weight_edit_text
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderFragment : Fragment() {

    private val viewModel: OrderViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        order_create_order_button.setOnClickListener {
            val gender = order_gender_edit_text.text.toString()
            val height = order_height_edit_text.text.toString()
            val weight = order_weight_edit_text.text.toString()
            val order = Order(gender, height.toInt(), weight.toInt())
            createOrder(order)
        }
    }

    private fun createOrder(order: Order) = viewModel.createOrder(order)
}
