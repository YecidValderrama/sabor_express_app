package com.saborexpress.seller

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.saborexpress.common.Order
import com.saborexpress.databinding.ItemSellerOrderBinding

class OrderAdapter(
    private var orders: List<Order>,
    private val onActionClick: (Order) -> Unit
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(val binding: ItemSellerOrderBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemSellerOrderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        with(holder.binding) {
            tvOrderTable.text = "${order.tableNumber} • [${order.status}]"
            tvOrderArrivalTime.text = order.arrivalTime
            tvOrderCustomerName.text = order.customerName
            tvOrderDishesList.text = order.dishesSummary

            btnSendToKitchen.text = when (order.status) {
                "Pendiente" -> "Comanda a Cocina"
                "En Preparación" -> "Marcar como Lista"
                else -> "Servida en Mesa"
            }

            btnSendToKitchen.setOnClickListener {
                onActionClick(order)
            }
        }
    }

    override fun getItemCount(): Int = orders.size

    fun filterByStatus(status: String, fullList: List<Order>) {
        orders = fullList.filter { it.status.equals(status, ignoreCase = true) }
        notifyDataSetChanged()
    }
}

