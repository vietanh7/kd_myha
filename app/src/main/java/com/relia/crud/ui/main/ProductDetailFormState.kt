package com.relia.crud.ui.main

/**
 * Data validation state of the product detail form.
 */
data class ProductDetailFormState(
    val skuError: Int? = null,
    val productNameError: Int? = null,
    val unitError: Int? = null,
    val isDataValid: Boolean = false
)