package com.relia.crud.data.product

import com.relia.crud.ui.ListAdapterItem
import java.io.Serializable

data class Product (
    val sku: String,
    val product_name: String?,
    val qty: Int?,
    val price: Long?,
    val unit: String?,
    val status: Short?,
    override val id: Long
): ListAdapterItem, Serializable {
    constructor(sku: String) : this(sku, null, null, null, null, null, -1)
}