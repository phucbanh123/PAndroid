package com.example.waaaaaaaa

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ProductAdapter(
    private val productList: List<Product>,
    private val onItemClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgProduct: ImageView   = itemView.findViewById(R.id.imgProduct)
        val nameProduct: TextView   = itemView.findViewById(R.id.nameProduct)
        val tvTechSpecs: TextView   = itemView.findViewById(R.id.tvTechSpecs)
        val priceProduct: TextView  = itemView.findViewById(R.id.priceProduct)
        val oldPriceProduct: TextView = itemView.findViewById(R.id.oldPriceProduct)
        val tvDiscount: TextView    = itemView.findViewById(R.id.tvDiscount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = productList[position]
        holder.nameProduct.text = product.title
        holder.tvTechSpecs.text = buildString {
            if (!product.brand.isNullOrBlank()) append("${product.brand} | ")
            append(product.category)
        }
        holder.priceProduct.text = "$${"%.2f".format(product.price)}"
        if (product.discountPercentage > 0) {
            val originalPrice = product.price / (1.0 - product.discountPercentage / 100.0)
            holder.oldPriceProduct.text = "$${"%.2f".format(originalPrice)}"
            holder.oldPriceProduct.paintFlags =
                holder.oldPriceProduct.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.oldPriceProduct.visibility = View.VISIBLE

            holder.tvDiscount.text = "-${product.discountPercentage.toInt()}%"
            holder.tvDiscount.visibility = View.VISIBLE
        } else {
            holder.oldPriceProduct.visibility = View.GONE
            holder.tvDiscount.visibility = View.GONE
        }
        Glide.with(holder.itemView.context)
            .load(product.thumbnail)
            .placeholder(R.mipmap.ic_launcher)
            .into(holder.imgProduct)

        holder.itemView.setOnClickListener { onItemClick(product) }
    }

    override fun getItemCount(): Int = productList.size
}