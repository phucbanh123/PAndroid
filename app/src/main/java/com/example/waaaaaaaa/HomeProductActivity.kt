package com.example.waaaaaaaa

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class HomeProductActivity : AppCompatActivity() {

    private lateinit var productAdapter: ProductAdapter
    private var productList = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home_product)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val recyclerView = findViewById<RecyclerView>(R.id.listProduct_view)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        productAdapter = ProductAdapter(productList) { product ->
            Toast.makeText(this, "Sản phẩm: ${product.title}", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = productAdapter
        loadProductsFromApi()
    }

    private fun loadProductsFromApi() {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.getProducts()
                if (response.products.isNotEmpty()) {
                    productList.clear()
                    productList.addAll(response.products)
                    productAdapter.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "Không thể kết nối: ${e.message}")
                Toast.makeText(this@HomeProductActivity, "Lỗi tải dữ liệu!", Toast.LENGTH_LONG).show()
            }
        }
    }
}