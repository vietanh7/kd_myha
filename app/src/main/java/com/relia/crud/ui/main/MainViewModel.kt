package com.relia.crud.ui.main

import android.content.SharedPreferences
import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.relia.crud.R
import com.relia.crud.data.product.Product
import com.relia.crud.data.product.ProductRepository
import com.relia.crud.data.remote.NetworkErrorResponse
import com.relia.crud.utils.Constant
import com.relia.crud.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val productRepository: ProductRepository
) : ViewModel() {
    private val _productList = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _productList
    private val _productDetailForm = MutableLiveData<ProductDetailFormState>()
    val productDetailFormState: LiveData<ProductDetailFormState> = _productDetailForm

    var selectedProduct: Product? = null

    val showProductDetail = MutableLiveData<Event<Product>>()
    val showToast = MutableLiveData<Event<String>>()
    val logUserOut = MutableLiveData<Event<Boolean>>()

    fun loadProductList() {
        productRepository.getProducts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result -> _productList.setValue(result) }
            ) { error ->
                showToast.value = Event("get products error: " + error.message)
                handleLogUserOutExpired(error)
            }
    }

    fun onSearch(sku: String) {
        productRepository.searchProductBySku(sku)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                _productList.value = arrayListOf(result)
            }
            ) { error ->
                showToast.value = Event("search products error: " + error.message)
                handleLogUserOutExpired(error)
            }
    }

    fun onAdd(product: Product) {
        productRepository.addProduct(product)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result -> showToast.value = Event("add product successfully") }
            ) { error ->
                showToast.value = Event("add products error: ${error.message}")
                handleLogUserOutExpired(error)
            }
    }

    fun onEdit(product: Product) {
        productRepository.updateProduct(product)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result -> showToast.value = Event("update product successfully") }
            ) { error ->
                showToast.value = Event("update products error: " + error.message)
                handleLogUserOutExpired(error)
            }

    }

    fun onDelete(product: Product) {
        productRepository.deleteProduct(product)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                showToast.value = Event("Delete product successfully")
                loadProductList()
            }
            ) { error ->
                showToast.value = Event("delete products error: " + error.message)
                handleLogUserOutExpired(error)
            }

    }

    fun onClick(product: Product) {
        showProductDetail.value = Event(product)
    }

    private fun handleLogUserOutExpired(error: Throwable) {
        if (error is HttpException) {
            val errorMessage: NetworkErrorResponse = Gson().fromJson(
                error.response()?.errorBody()?.string(),
                object : TypeToken<NetworkErrorResponse>() {}.type
            )
            if (errorMessage.error.toString() == "Provided token is expired.") {
                sharedPreferences.edit().putString(Constant.TOKEN_KEY, "").apply()
                logUserOut.value = Event(true)
            }
        }
    }


    fun productDetailDataChanged(sku: String, productName: String, unit: String) {
        when {
            TextUtils.isEmpty(sku) -> {
                _productDetailForm.value = ProductDetailFormState(skuError = R.string.blank_sku)
            }
            TextUtils.isEmpty(productName) -> {
                _productDetailForm.value =
                    ProductDetailFormState(productNameError = R.string.blank_product_name)
            }
            TextUtils.isEmpty(unit) -> {
                _productDetailForm.value = ProductDetailFormState(unitError = R.string.blank_unit)
            }
            else -> {
                _productDetailForm.value = ProductDetailFormState(isDataValid = true)
            }

        }
    }
}