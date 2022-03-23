package com.relia.crud.data.product

import com.google.gson.annotations.SerializedName

data class SearchProductRequest(
    @SerializedName("sku")
    var sku: String = ""
)