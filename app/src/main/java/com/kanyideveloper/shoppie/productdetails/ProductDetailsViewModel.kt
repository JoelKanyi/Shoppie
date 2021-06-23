package com.kanyideveloper.shoppie.productdetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kanyideveloper.shoppie.model.Product

class ProductDetailsViewModel(private val product: Product, application: Application) :
    AndroidViewModel(application) {

    private val firestoreDatabase = Firebase.firestore

    private val _productDetails = MutableLiveData<Product>()
    val productDetails: LiveData<Product>
        get() = _productDetails

    private val _cartEmpty = MutableLiveData<Boolean>()
    val cartStatus: LiveData<Boolean>
        get() = _cartEmpty

    private val _addedToCart = MutableLiveData<Boolean>()
    val addedToCart: LiveData<Boolean>
        get() = _addedToCart

    init {
        _productDetails.value = product
        checkCart()
    }

    private fun checkCart() {
        firestoreDatabase.collection("cart_items").get().addOnCompleteListener {
            if (it.isSuccessful) {
                _cartEmpty.value = it.result!!.documents.isEmpty()
            }
        }
    }

    fun addItemToCart(product: Product) {
        firestoreDatabase.collection("cart_items").add(product).addOnSuccessListener {
            _addedToCart.value = true
        }.addOnFailureListener {
            _addedToCart.value = false
        }
    }
}