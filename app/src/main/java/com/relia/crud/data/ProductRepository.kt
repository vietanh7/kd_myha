package com.relia.crud.data

interface ProductRepository {
    fun getProducts(): List<Product>
}