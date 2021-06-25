package com.kanyideveloper.shoppie.products

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kanyideveloper.shoppie.model.Product


enum class Status { LOADING, ERROR, DONE }

private const val TAG = "ProductViewModel"

class ProductViewModel : ViewModel() {

    private val firestoreDatabase = Firebase.firestore

    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status>
        get() = _status

    private val _navigateToSelectedItem = MutableLiveData<Product>()
    val navigateToSelectedItem: LiveData<Product>
        get() = _navigateToSelectedItem

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>>
        get() = _products

    private val _cartEmpty = MutableLiveData<Boolean>()
    val cartStatus: LiveData<Boolean>
        get() = _cartEmpty

    private val _navigateToCart = MutableLiveData<Boolean>()
    val navigateToCart: LiveData<Boolean>
        get() = _navigateToCart

    init {
        checkCart()
        getAllProducts()
    }

    private fun getAllProducts() {

        _status.value = Status.LOADING

        firestoreDatabase.collection("products").get().addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d(TAG, "getAllProducts: Success")
                if (it.result!!.documents.isNotEmpty()) {
                    val prodList = ArrayList<Product>()
                    for (document in it.result!!) {
                        val prod = document.toObject(Product::class.java)
                        prodList.add(prod)
                        Log.d(TAG, "getAllProducts: $prodList")
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

    fun displaySelectedProduct(product: Product) {
        _navigateToSelectedItem.value = product
    }

    fun displayProductDetailsCompleted() {
        _navigateToSelectedItem.value = null
    }

    fun navigatingToCartCompleted() {
        _navigateToCart.value = false
    }

    fun navigateToCart() {
        _navigateToCart.value = true
    }

    private fun checkCart() {
        firestoreDatabase.collection("cart_items").get().addOnCompleteListener {
            if (it.isSuccessful) {
                _cartEmpty.value = it.result!!.documents.isEmpty()
            }
        }
    }
}