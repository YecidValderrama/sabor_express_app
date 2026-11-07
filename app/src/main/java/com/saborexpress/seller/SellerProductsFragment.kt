package com.saborexpress.seller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.saborexpress.common.MockDataProvider
import com.saborexpress.databinding.FragmentSellerProductsBinding

/**
 * Fragmento de Panel de Productos para el Rol Vendedor.
 * Muestra el catálogo de la sede con opciones interactivas de editar y eliminar.
 */
class SellerProductsFragment : Fragment() {

    private var _binding: FragmentSellerProductsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: SellerProductAdapter
    private val productsList = MockDataProvider.catalogDishes.toMutableList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSellerProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupListeners()
    }

    private fun setupRecyclerView() {
        adapter = SellerProductAdapter(
            productsList,
            onEditClick = { product ->
                Toast.makeText(requireContext(), "Editar producto: ${product.name}", Toast.LENGTH_SHORT).show()
            },
            onDeleteClick = { product, position ->
                productsList.removeAt(position)
                adapter.notifyItemRemoved(position)
                Toast.makeText(requireContext(), "'${product.name}' eliminado del catálogo", Toast.LENGTH_SHORT).show()
            }
        )
        binding.rvSellerProducts.adapter = adapter
    }

    private fun setupListeners() {
        binding.btnAddProduct.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Formulario: Crear nuevo producto gastronómico...",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
