package com.relia.crud.data.remote

import com.relia.crud.data.Product
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.ArrayList

interface ProductService {
    @GET("items")
    fun getAllProducts() : Observable<ArrayList<Product>>

    @POST("search")
    fun searchProduct(@Body request: SearchProductRequest): Observable<Product>
}