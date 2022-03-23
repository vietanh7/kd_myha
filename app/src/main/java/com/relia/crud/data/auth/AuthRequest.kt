package com.relia.crud.data.auth

import java.io.Serializable

class AuthRequest(var email: String, var password: String) : Serializable {
    val params: Map<String, String>
        get() {
            val params: MutableMap<String, String> = HashMap()
            params["email"] = email
            params["password"] = password
            return params
        }

}