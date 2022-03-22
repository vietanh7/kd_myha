package com.relia.crud.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.relia.crud.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        observeListProduct()
    }

    private fun observeListProduct() {
        viewModel.products.observe(this) {
            val products = findViewById<RecyclerView>(R.id.rvProduct)
            products.layoutManager = LinearLayoutManager(this)
            products.adapter = ProductAdapter(it)
        }
    }
}