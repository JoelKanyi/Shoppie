package com.kanyideveloper.shoppie.productdetails

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kanyideveloper.shoppie.model.Product
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class ProductDetailsViewModelFactory(private val product: Product, private val application: Application)  : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductDetailsViewModel::class.java)){
            return ProductDetailsViewModel(product, application) as T
        }
        throw IllegalArgumentException("Unknow Class")
    }
}