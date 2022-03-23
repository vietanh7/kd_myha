package com.relia.crud.data.product

import io.reactivex.rxjava3.core.Observable
import java.util.ArrayList
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productService: ProductService
) {
    fun getProducts(): Observable<ArrayList<Product>> = productService.getAllProducts()
    fun updateProduct(product: Product): Observable<String> = productService.updateProduct(ProductRequest(product).params)
    fun deleteProduct(product: Product): Observable<String> = productService.deleteProduct(ProductRequest(product).params)
    fun searchProductBySku(sku:String) = productService.searchProduct(SearchProductRequest(sku))
}