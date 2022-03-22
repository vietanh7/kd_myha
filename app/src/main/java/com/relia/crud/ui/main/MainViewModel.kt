package com.relia.crud.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.relia.crud.data.Product
import com.relia.crud.data.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {
    private val productListEmitter = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = productListEmitter
    init {
        loadProductList()
    }
    private fun loadProductList() {
        productListEmitter.value = productRepository.getProducts()
    }
}