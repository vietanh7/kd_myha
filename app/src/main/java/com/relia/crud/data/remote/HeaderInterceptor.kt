package com.relia.crud.data.remote

import android.content.SharedPreferences
import android.text.TextUtils
import com.relia.crud.Constant.TOKEN_KEY
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor @Inject constructor(private val sharedPreferences: SharedPreferences) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val accessToken = sharedPreferences.getString(TOKEN_KEY, "")
        val original = chain.request()
        val requestBuilder = original.newBuilder()

        if (!TextUtils.isEmpty(accessToken)) {
            requestBuilder.addHeader("Authorization", "Bearer $accessToken")
        }
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}

