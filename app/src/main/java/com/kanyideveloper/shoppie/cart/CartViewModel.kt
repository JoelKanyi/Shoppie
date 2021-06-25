package com.kanyideveloper.shoppie.cart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kanyideveloper.shoppie.model.Product


private const val TAG = "CartViewModel"

enum class Status { LOADING, ERROR, DONE }

class CartViewModel : ViewModel() {
    private val firestoreDatabase = Firebase.firestore

    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status>
        get() = _status


    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>>
        get() = _products

    private val _cartEmpty = MutableLiveData<Boolean>()
    val cartStatus: LiveData<Boolean>
        get() = _cartEmpty

    init {
        checkCart()
        getCartProducts()
    }

    private fun getCartProducts() {

        _status.value = Status.LOADING

        firestoreDatabase.collection("cart_items").get().addOnCompleteListener {
            if (it.isSuccessful) {
                if (it.result!!.documents.isNotEmpty()) {
                    val prodList = ArrayList<Product>()
                    for (document in it.result!!) {
                        val prod = document.toObject(Product::class.java)
                        prodList.add(prod)
                    }
                    _status.value = Status.DONE
                    _products.value = prodList
                } else {
                    _status.value = Status.DONE
                    _products.value = ArrayList()
                }
            } else {
                Log.d(TAG, "getAllProducts: Not Successful")
                _status.value = Status.ERROR
                _products.value = ArrayList()
            }
        }.addOnFailureListener {
            Log.d(TAG, "getAllProducts: Error ${it.message}")
            _status.value = Status.ERROR
            _products.value = ArrayList()
        }
    }

    private fun checkCart() {
        firestoreDatabase.collection("cart_items").get().addOnCompleteListener {
            if (it.isSuccessful) {
                _cartEmpty.value = it.result!!.documents.isEmpty()
            }
        }
    }
}