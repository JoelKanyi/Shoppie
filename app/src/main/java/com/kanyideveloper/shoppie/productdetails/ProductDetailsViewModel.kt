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

    private val _addedToCart = MutableLiveData<Boolean>()
    val addedToCart: LiveData<Boolean>
        get() = _addedToCart

    private val _backToProducts = MutableLiveData<Boolean>()
    val backTOProducts: LiveData<Boolean>
        get() = _backToProducts

    init {
        _productDetails.value = product
    }

    fun addItemToCart(product: Product) {
        firestoreDatabase.collection("cart_items").add(product).addOnSuccessListener {
            _addedToCart.value = true
        }.addOnFailureListener {
            _addedToCart.value = false
        }
    }

    fun navigateBackToProducts() {
        _backToProducts.value = true
    }

    fun navigatingBackCompleted() {
        _backToProducts.value = false
    }
}