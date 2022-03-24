package com.relia.crud.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.relia.crud.R
import com.relia.crud.data.product.Product
import com.relia.crud.databinding.FragmentAddProductBinding
import com.relia.crud.ui.login.LoginActivity
import com.relia.crud.utils.ViewUtils.afterTextChanged
import dagger.hilt.android.AndroidEntryPoint
import androidx.appcompat.app.AppCompatActivity





@AndroidEntryPoint
class NewProductFragment : Fragment(R.layout.fragment_add_product) {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentAddProductBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(requireActivity())[MainViewModel::class.java]
        binding = FragmentAddProductBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.title = getString(R.string.add_new_product)
        val actionBar = (activity as AppCompatActivity?)?.supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeButtonEnabled(true)

        viewModel.showToast.observe(this) { event ->
            event.getContentIfNotHandled()?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.showToastResInt.observe(this) { event ->
            event.getContentIfNotHandled()?.let {
                Toast.makeText(requireContext(), getString(it), Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.logUserOut.observe(this) { event ->
            event.getContentIfNotHandled()?.let {
                if (it) startActivity(Intent(requireActivity(), LoginActivity::class.java))
            }
        }


        binding.btnSave.setOnClickListener {
            viewModel.onAdd(
                Product(
                    sku = binding.etSku.text.toString(),
                    product_name = binding.etProductName.text.toString(),
                    qty = binding.etQuantity.text.toString().toIntOrNull()?:0,
                    price = binding.etPrice.text.toString().toLongOrNull()?:0,
                    unit = binding.etUnit.text.toString(),
                    status = binding.etStatus.text.toString().toShortOrNull()?:0
                )
            )

        }

        viewModel.productDetailFormState.observe(this, Observer {
            val registerState = it ?: return@Observer

            // disable register button unless both username / password is valid
            binding.btnSave.isEnabled = registerState.isDataValid

            if (registerState.skuError != null) {
                binding.etSku.error = getString(registerState.skuError)
            }
            if (registerState.productNameError != null) {
                binding.etProductName.error = getString(registerState.productNameError)
            }
            if (registerState.unitError != null) {
                binding.etUnit.error = getString(registerState.unitError)
            }
        })

        checkFormState()
        binding.etSku.afterTextChanged {
            checkFormState()
        }
        binding.etProductName.afterTextChanged {
            checkFormState()
        }
        binding.etUnit.afterTextChanged {
            checkFormState()
        }
    }

    private fun checkFormState() {
        viewModel.productDetailDataChanged(
            binding.etSku.text.toString(),
            binding.etProductName.text.toString(),
            binding.etUnit.text.toString()
        )
    }
}
