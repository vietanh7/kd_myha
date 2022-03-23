package com.relia.crud.ui.main

import com.relia.crud.data.product.Product

interface ProductListener {
    fun onClick(product:Product)
    fun onDelete(product:Product)
}