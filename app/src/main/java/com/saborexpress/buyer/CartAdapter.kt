package com.saborexpress.buyer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.saborexpress.common.Dish
import com.saborexpress.databinding.ItemCartDishBinding

class CartAdapter(
    private val items: MutableList<Dish>,
    private val onQuantityChanged: () -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(val binding: ItemCartDishBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartDishBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val dish = items[position]
        with(holder.binding) {
            tvCartDishTitle.text = dish.name
            tvCartDishPrice.text = dish.formattedPrice()
            tvCartDishQuantity.text = dish.quantity.toString()

            btnIncreaseQty.setOnClickListener {
                dish.quantity++
                tvCartDishQuantity.text = dish.quantity.toString()
                onQuantityChanged()
            }

            btnDecreaseQty.setOnClickListener {
                if (dish.quantity > 1) {
                    dish.quantity--
                    tvCartDishQuantity.text = dish.quantity.toString()
                    onQuantityChanged()
                } else {
                    val currentPos = holder.adapterPosition
                    if (currentPos != RecyclerView.NO_POSITION) {
                        items.removeAt(currentPos)
                        notifyItemRemoved(currentPos)
                        onQuantityChanged()
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = items.size
}
