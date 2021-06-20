package com.kanyideveloper.shoppie.productdetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kanyideveloper.shoppie.model.Product

class ProductDetailsViewModel(private val product: Product, application: Application ) : AndroidViewModel(application) {

    private val _productDetails = MutableLiveData<Product>()
    val productDetails: LiveData<Product>
        get() = _productDetails

    init {
        _productDetails.value = product
    }
}