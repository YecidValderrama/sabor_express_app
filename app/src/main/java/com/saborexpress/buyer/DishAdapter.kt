package com.saborexpress.buyer

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.saborexpress.common.Dish
import com.saborexpress.databinding.ItemDishCardBinding

class DishAdapter(
    private var dishes: List<Dish>,
    private val onAddClick: (Dish) -> Unit
) : RecyclerView.Adapter<DishAdapter.DishViewHolder>() {

    inner class DishViewHolder(val binding: ItemDishCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        val binding = ItemDishCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DishViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        val dish = dishes[position]
        with(holder.binding) {
            tvDishTitle.text = dish.name
            tvDishDescription.text = dish.description
            tvDishPrice.text = dish.formattedPrice()

            btnAddDish.setOnClickListener {
                onAddClick(dish)
            }
        }
    }

    override fun getItemCount(): Int = dishes.size

    fun filter(query: String, fullList: List<Dish>) {
        dishes = if (query.isEmpty()) {
            fullList
        } else {
            fullList.filter {
                it.name.contains(query, ignoreCase = true) ||
                it.category.contains(query, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }
}

