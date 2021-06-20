package com.kanyideveloper.shoppie.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {

    private val _res = MutableLiveData<Int>()
    val res: LiveData<Int>
        get() = _res


    fun addTwoNums(){
        _res.value = 2+2
    }
}