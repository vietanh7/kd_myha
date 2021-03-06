package com.relia.crud.data.product

import io.reactivex.rxjava3.core.Observable
import java.util.ArrayList
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productService: ProductService
) {
    fun getProducts(): Observable<ArrayList<Product>> = productService.getAllProducts()
    fun addProduct(product: Product): Observable<ProductResponse> = productService.addProduct(ProductRequest(product).params)
    fun updateProduct(product: Product): Observable<ProductResponse> = productService.updateProduct(ProductRequest(product).params)
    fun deleteProduct(product: Product): Observable<ProductResponse> = productService.deleteProduct(ProductRequest(product).params)
    fun searchProductBySku(sku:String) = productService.searchProduct(SearchProductRequest(sku))
}