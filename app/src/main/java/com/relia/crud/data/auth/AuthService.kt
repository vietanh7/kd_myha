package com.relia.crud.data.auth

import com.relia.crud.data.remote.NetworkResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

interface AuthService {
    @FormUrlEncoded
    @POST("auth/login")
    fun login(@FieldMap body: Map<String, String>) : Observable<AuthToken>

    @FormUrlEncoded
    @POST("register")
    fun register(@FieldMap body: Map<String, String>) : Observable<NetworkResponse<User>>
}