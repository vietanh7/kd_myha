package com.relia.crud.data.product

import java.io.Serializable

class ProductRequest(var product: Product) : Serializable {
    val params: Map<String, String>
        get() {
            val params: MutableMap<String, String> = HashMap()
            params["sku"] = product.sku
            params["product_name"] = product.product_name.toString()
            params["qty"] = product.qty.toString()
            params["price"] = product.price.toString()
            params["unit"] = product.unit.toString()
            params["status"] = product.status.toString()
            return params
        }
}