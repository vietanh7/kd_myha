package com.relia.crud.data


class ProductRepositoryImpl : ProductRepository {
    override fun getProducts() = listOf(
        Product("1", "Product 1"),
        Product("2", "Product 2"),
        Product("3", "Product 3"),
        Product("4", "Product 4"),
        Product("5", "Product 5"),
    )
}