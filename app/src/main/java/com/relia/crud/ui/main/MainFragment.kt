package com.relia.crud.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import com.relia.crud.R
import com.relia.crud.data.product.Product
import com.relia.crud.databinding.FragmentMainBinding
import com.relia.crud.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main), ProductListener {
    private var navController: NavController? = null
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentMainBinding
    private var searchView: SearchView? = null
    private var queryTextListener: SearchView.OnQueryTextListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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

        activity?.title = getString(R.string.product_list)
        val actionBar = (activity as AppCompatActivity?)?.supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(false)
        actionBar?.setHomeButtonEnabled(false)

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

        viewModel.showProductDetail.observe(this) { event ->
            event.getContentIfNotHandled()?.let {
                viewModel.selectedProduct = it
                navController?.navigate(R.id.action_mainFragment_to_productDetailFragment)
            }
        }

        viewModel.logUserOut.observe(this) { event ->
            event.getContentIfNotHandled()?.let {
                if (it) startActivity(Intent(requireActivity(), LoginActivity::class.java))
            }
        }

        binding.btnAdd.setOnClickListener {
            navController?.navigate(R.id.action_mainFragment_to_newProductFragment)
        }

        binding.swipe.setOnRefreshListener {
            viewModel.loadProductList()
            Handler(Looper.getMainLooper()).postDelayed({
                binding.swipe.isRefreshing = false
            }, 2000)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchManager =
            activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        if (searchItem != null) {
            searchView = searchItem.actionView as SearchView
        }
        if (searchView != null) {
            searchView?.queryHint = "Search by SKU"
            searchView?.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
            queryTextListener = object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String): Boolean {
                    if (TextUtils.isEmpty(newText)) {
                        viewModel.loadProductList()
                    }
                    return true
                }

                override fun onQueryTextSubmit(query: String): Boolean {
                    searchView?.clearFocus()
                    viewModel.onSearch(query)
                    return true
                }
            }
            searchView?.setOnQueryTextListener(queryTextListener)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search ->
                return false
            else -> {}
        }
        searchView?.setOnQueryTextListener(queryTextListener)
        return super.onOptionsItemSelected(item)
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