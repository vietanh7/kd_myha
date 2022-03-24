package com.relia.crud.data.product

import java.io.Serializable

class ProductResponse(
    val id: Long? = null,
    val sku: String,
    val product_name: String? = null,
    val qty: Int? = null,
    val price: Long? = null,
    val unit: String? = null,
    val status: Short? = null,
    val success: Boolean? = null,
    val message: String? = null
) : Serializable {
    fun getProduct(): Product = Product(id, sku, product_name, qty, price, unit, status)
    fun getErrorMessage(): String? = if(success==false) message else null
}