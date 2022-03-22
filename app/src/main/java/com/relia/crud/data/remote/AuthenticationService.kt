package com.relia.crud.data.remote

import com.relia.crud.data.Product
import com.relia.crud.data.User
import io.reactivex.rxjava3.core.Observable
import okhttp3.RequestBody
import retrofit2.http.*
import java.util.ArrayList

interface AuthenticationService {
    @FormUrlEncoded
    @POST("auth/login")
    fun login(@FieldMap body: Map<String, String>) : Observable<String>

    @FormUrlEncoded
    @POST("auth/login")
    fun login2(@Field("email") email: String,
              @Field("password") password: String) : Observable<String>

    @Multipart
    @POST("register")
    fun register(@Path("email") email: String,
              @Path("password") password: String): Observable<NetworkResponse<User>>
}