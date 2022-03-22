package com.relia.crud.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.relia.crud.data.Product
import com.relia.crud.data.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import android.util.Log

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

import java.util.ArrayList

import io.reactivex.rxjava3.schedulers.Schedulers

@HiltViewModel
class MainViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {
    private val productListLiveData = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = productListLiveData
    init {
        loadProductList()
    }

    private fun loadProductList() {
        productRepository.getProducts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result -> productListLiveData.setValue(result) }
            ) { error -> Log.e("TAG", "get products error: " + error.message) }
    }

    private fun searchProductBySku(sku: String) {
        productRepository.searchProductBySku(sku)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result -> productListLiveData.setValue(arrayListOf(result)) }
            ) { error -> Log.e("TAG", "search products error: " + error.message) }
    }
}