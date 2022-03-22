package com.relia.crud.data

import com.relia.crud.data.remote.*
import io.reactivex.rxjava3.core.Observable
import okhttp3.MultipartBody
import java.util.ArrayList
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(
    private val authenticationService: AuthenticationService
) {
    fun login(email:String, password: String): Observable<String> = authenticationService.login(LoginRequest(email, password).params)
    fun register(email:String, password: String): Observable<NetworkResponse<User>> = authenticationService.register(email, password)
}