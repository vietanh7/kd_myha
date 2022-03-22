package com.relia.crud.ui.login.data

import com.relia.crud.data.User
import com.relia.crud.data.remote.AuthenticationService
import com.relia.crud.data.remote.LoginRequest
import com.relia.crud.data.remote.NetworkResponse
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository @Inject constructor(
    private val authenticationService: AuthenticationService
) {

    fun login(email: String, password: String): Observable<String> =
        authenticationService.login(LoginRequest(email, password).params)

    fun register(email: String, password: String): Observable<NetworkResponse<User>> =
        authenticationService.register(email, password)
}