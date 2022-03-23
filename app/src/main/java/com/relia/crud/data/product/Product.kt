package com.relia.crud.data.product

import com.relia.crud.ui.ListAdapterItem
import java.io.Serializable

class Product(
    val id: Long? = null,
    val sku: String,
    val product_name: String? = null,
    val qty: Int? = null,
    val price: Long? = null,
    val unit: String? = null,
    val status: Short? = null,
    override val idAdapter: Long? = null
) : ListAdapterItem, Serializable