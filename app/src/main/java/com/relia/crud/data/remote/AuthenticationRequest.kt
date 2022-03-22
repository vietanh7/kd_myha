package com.relia.crud.data.remote

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AuthenticationRequest(
    @SerializedName("email")
    var email: String = "",
    @SerializedName("password")
    var password: String = ""
)