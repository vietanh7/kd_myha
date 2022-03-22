package com.relia.crud.data.remote

data class NetworkResponse<T> (
    var success : Boolean,
    var message: String?,
    var data: T?,
)
