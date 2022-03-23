package com.relia.crud.ui.main

import com.relia.crud.data.product.Product

interface ProductListener {
    fun onEdit(product:Product)
    fun onDelete(product:Product)
}