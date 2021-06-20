package com.kanyideveloper.shoppie.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kanyideveloper.shoppie.model.Product


enum class Status{ LOADING, ERROR, DONE}

class ProductViewModel : ViewModel() {

    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status>
        get() = _status

    private val _navigateToSelectedItem = MutableLiveData<Product>()
    val navigateToSelectedItem: LiveData<Product>
        get() = _navigateToSelectedItem

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>>
        get() = _products

    init {
        getAllProducts()
    }

    private fun getAllProducts(){
        try {
            _status.value = Status.LOADING
            /*if (response.isNotEmpty){
                _status.value = Status.DONE
                _products.value = response
            }*/
        }catch (e: Exception){
            _status.value = Status.ERROR
            _products.value = ArrayList()
        }
    }

    fun displaySelectedProduct(product: Product){
        _navigateToSelectedItem.value = product
    }

    fun displayProductDetailsCompleted(){
        _navigateToSelectedItem.value = null
    }
}