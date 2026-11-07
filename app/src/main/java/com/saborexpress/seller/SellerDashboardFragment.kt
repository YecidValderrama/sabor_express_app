package com.saborexpress.seller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.saborexpress.R
import com.saborexpress.common.MockDataProvider
import com.saborexpress.databinding.FragmentSellerDashboardBinding

/**
 * Fragmento de Pase de Cocina para el Rol Vendedor con datos mockeados y acciones interactivas.
 */
class SellerDashboardFragment : Fragment() {

    private var _binding: FragmentSellerDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: OrderAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSellerDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupListeners()
    }

    private fun setupRecyclerView() {
        adapter = OrderAdapter(MockDataProvider.ordersList.filter { it.status == "Pendiente" }) { order ->
            when (order.status) {
                "Pendiente" -> {
                    order.status = "En Preparación"
                    Toast.makeText(requireContext(), "Comanda enviada a cocina (${order.tableNumber})", Toast.LENGTH_SHORT).show()
                }
                "En Preparación" -> {
                    order.status = "Lista"
                    Toast.makeText(requireContext(), "Platos listos para entregar en (${order.tableNumber})", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(requireContext(), "Comanda finalizada en mesa", Toast.LENGTH_SHORT).show()
                }
            }
            updateListBySelectedChip()
        }
        binding.rvSellerOrders.adapter = adapter
    }

    private fun updateListBySelectedChip() {
        val checkedId = binding.cgOrderStatus.checkedChipId
        when (checkedId) {
            R.id.chipPending -> adapter.filterByStatus("Pendiente", MockDataProvider.ordersList)
            R.id.chipInPrep -> adapter.filterByStatus("En Preparación", MockDataProvider.ordersList)
            R.id.chipReady -> adapter.filterByStatus("Lista", MockDataProvider.ordersList)
            else -> adapter.filterByStatus("Pendiente", MockDataProvider.ordersList)
        }
    }

    private fun setupListeners() {
        binding.cgOrderStatus.setOnCheckedStateChangeListener { _, checkedIds ->
            when {
                checkedIds.contains(R.id.chipPending) -> {
                    adapter.filterByStatus("Pendiente", MockDataProvider.ordersList)
                }
                checkedIds.contains(R.id.chipInPrep) -> {
                    adapter.filterByStatus("En Preparación", MockDataProvider.ordersList)
                }
                checkedIds.contains(R.id.chipReady) -> {
                    adapter.filterByStatus("Lista", MockDataProvider.ordersList)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
