package com.relia.crud.data.remote

import java.lang.RuntimeException

data class NetworkErrorResponse(
    var error: String?,
    var email: ArrayList<String>?,
) : RuntimeException()
