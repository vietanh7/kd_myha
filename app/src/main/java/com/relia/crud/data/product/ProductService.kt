package com.relia.crud.data.product

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

interface ProductService {
    @GET("items")
    fun getAllProducts(): Observable<ArrayList<Product>>

    @POST("item/search")
    fun searchProduct(@Body request: SearchProductRequest): Observable<Product>

    @FormUrlEncoded
    @POST("item/add")
    fun addProduct(@FieldMap body: Map<String, String>): Observable<String>

    @FormUrlEncoded
    @POST("item/update")
    fun updateProduct(@FieldMap body: Map<String, String>): Observable<String>

    @FormUrlEncoded
    @POST("item/delete")
    fun deleteProduct(@FieldMap body: Map<String, String>): Observable<String>

}