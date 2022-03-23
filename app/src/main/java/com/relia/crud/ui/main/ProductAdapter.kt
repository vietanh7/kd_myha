package com.relia.crud.ui.main

import com.relia.crud.R
import com.relia.crud.data.product.Product
import com.relia.crud.databinding.ItemMainProductBinding
import com.relia.crud.ui.BaseAdapter
import com.relia.crud.ui.ListAdapterItem

class ProductAdapter(
    private val list: List<Product>,
    private val productListener: ProductListener
) : BaseAdapter<ItemMainProductBinding, Product>(list) {

    override val layoutId: Int = R.layout.item_main_product

    override fun bind(binding: ItemMainProductBinding, item: Product) {
        binding.apply {
            product = item
            listener = productListener
            executePendingBindings()
        }
    }
}