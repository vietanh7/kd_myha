package com.relia.crud.data.remote

import io.reactivex.rxjava3.core.Observable
import retrofit2.HttpException

data class NetworkResponse<T> (
    var success : Boolean,
    var message: String?,
    var data: T?,
)
fun <T> Observable<T>.mapNetworkErrors(): Observable<T> =
    this.onErrorResumeNext{
            error -> when (error) {
        is HttpException -> Observable.error(HttpCallFailureException(error))
        else -> Observable.error(error)
    }
}
open class NetworkException(error: Throwable): RuntimeException(error)

class HttpCallFailureException(error: Throwable): NetworkException(error)