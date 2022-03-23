package com.relia.crud.data.auth

import com.relia.crud.data.remote.NetworkResponse
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject


class AuthRepository @Inject constructor(
    private val authService: AuthService
) {

    fun login(email: String, password: String): Observable<AuthToken> =
        authService.login(AuthRequest(email, password).params)

    fun register(email: String, password: String): Observable<NetworkResponse<User>> =
        authService.register(AuthRequest(email, password).params)
}