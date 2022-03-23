package com.relia.crud.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.relia.crud.data.product.Product
import com.relia.crud.data.product.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import android.util.Log
import com.relia.crud.utils.Event

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

import io.reactivex.rxjava3.schedulers.Schedulers

@HiltViewModel
class MainViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {
    private val _productList = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _productList
    var selectedProduct: Product? = null

    val showProductDetail = MutableLiveData<Event<Product>>()
    val showToast = MutableLiveData<Event<String>>()

    fun loadProductList() {
        productRepository.getProducts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result -> _productList.setValue(result) }
            ) { error -> Log.e("TAG", "get products error: " + error.message) }
    }

    private fun searchProductBySku(sku: String) {
        productRepository.searchProductBySku(sku)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result -> _productList.setValue(arrayListOf(result)) }
            ) { error -> Log.e("TAG", "search products error: " + error.message) }
    }

    fun onEdit(product:Product) {
        productRepository.updateProduct(product)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->  showToast.value = Event("update product successfully: $result")}
            ) { error -> Log.e("TAG", "update products error: " + error.message) }

    }

    fun onDelete(product:Product) {
        productRepository.deleteProduct(product)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->  Log.d("TAG", "delete products success: $result")}
            ) { error -> Log.e("TAG", "update products error: " + error.message) }

    }

    fun onClick(product:Product) {
        showProductDetail.value = Event(product)
    }

}