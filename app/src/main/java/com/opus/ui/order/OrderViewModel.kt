package com.opus.ui.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch
import java.util.*

class OrderViewModel(private val database: FirebaseDatabase) : ViewModel() {

    fun createOrder(order: Order) {
        viewModelScope.launch {
            val key = database.reference.child("orders").push().key ?: UUID.randomUUID().toString()
            order.uuid = key
            database.reference.child("orders").child(key).setValue(order)
        }
    }

}

data class Order(val gender: String, val height: Int, val weight: Int, var uuid: String = "")