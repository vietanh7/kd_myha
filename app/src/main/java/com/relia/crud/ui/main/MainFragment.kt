package com.relia.crud.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import com.relia.crud.R
import com.relia.crud.data.product.Product
import com.relia.crud.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main), ProductListener {
    private var navController: NavController? = null
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(requireActivity())[MainViewModel::class.java]

        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.apply {
            viewModel = this@MainFragment.viewModel
            lifecycleOwner = requireActivity()
            adapter = ProductAdapter(listOf(), this@MainFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController(view)

        viewModel.showProductDetail.observe(this) { event ->
            event.getContentIfNotHandled()?.let {
                viewModel.selectedProduct = it
                navController?.navigate(R.id.action_mainFragment_to_productDetailFragment)
            }
        }

        binding.btnAdd.setOnClickListener {

        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadProductList()
    }

    override fun onClick(product: Product) {
        viewModel.onClick(product)
    }

    override fun onDelete(product: Product) {
        viewModel.onDelete(product)
    }
}