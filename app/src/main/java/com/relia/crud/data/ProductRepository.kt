package com.relia.crud.data

import com.relia.crud.data.remote.ProductService
import com.relia.crud.data.remote.SearchProductRequest
import io.reactivex.rxjava3.core.Observable
import java.util.ArrayList
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productService: ProductService
) {
    fun getProducts(): Observable<ArrayList<Product>> = productService.getAllProducts()
    fun searchProductBySku(sku:String) = productService.searchProduct(SearchProductRequest(sku))
}