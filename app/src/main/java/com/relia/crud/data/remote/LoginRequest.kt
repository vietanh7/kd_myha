package com.relia.crud.data.remote

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class LoginRequest(var email: String, var password: String) : Serializable {
    val params: Map<String, String>
        get() {
            val params: MutableMap<String, String> = HashMap()
            params["email"] = email
            params["password"] = password
            return params
        }

}