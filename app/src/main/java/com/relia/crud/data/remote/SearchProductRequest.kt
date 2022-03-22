package com.relia.crud.data.remote

import com.google.gson.annotations.SerializedName

data class SearchProductRequest(
    @SerializedName("sku")
    var sku: String = ""
)