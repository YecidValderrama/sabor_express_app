package com.saborexpress.seller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.saborexpress.common.Dish
import com.saborexpress.databinding.ItemSellerProductBinding

class SellerProductAdapter(
    private val products: MutableList<Dish>,
    private val onEditClick: (Dish) -> Unit,
    private val onDeleteClick: (Dish, Int) -> Unit
) : RecyclerView.Adapter<SellerProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(val binding: ItemSellerProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemSellerProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        with(holder.binding) {
            tvProductName.text = product.name
            tvProductPrice.text = product.formattedPrice()
            tvProductCategory.text = "${product.category} • Disponible"

            btnEditProduct.setOnClickListener {
                onEditClick(product)
            }

            btnDeleteProduct.setOnClickListener {
                val currentPos = holder.adapterPosition
                if (currentPos != RecyclerView.NO_POSITION) {
                    onDeleteClick(product, currentPos)
                }
            }
        }
    }

    override fun getItemCount(): Int = products.size
}
