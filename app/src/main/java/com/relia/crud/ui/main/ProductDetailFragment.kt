package com.relia.crud.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.relia.crud.R
import com.relia.crud.data.product.Product
import com.relia.crud.databinding.FragmentProductDetailBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProductDetailFragment : Fragment(R.layout.fragment_product_detail), ProductDetailListener {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentProductDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(requireActivity())[MainViewModel::class.java]
        binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        binding.apply {
            product = viewModel.selectedProduct
            listener = this@ProductDetailFragment
            executePendingBindings()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.showToast.observe(this) { event ->
            event.getContentIfNotHandled()?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnBack.setOnClickListener { requireActivity().onBackPressed() }
    }

    override fun onEdit() {
        viewModel.onEdit(Product(sku = binding.etSku.text.toString(),
            product_name = binding.etProductName.text.toString(),
            qty = binding.etQuantity.text.toString().toInt(),
            price = binding.etPrice.text.toString().toLong(),
            unit = binding.etUnit.text.toString(),
            status = binding.etStatus.text.toString().toShort()))
    }

    override fun onDelete() {
        viewModel.onDelete(Product(sku = binding.etSku.text.toString()))
    }
}

interface ProductDetailListener {
    fun onEdit()
    fun onDelete()
}
