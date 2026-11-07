package com.saborexpress.buyer

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.saborexpress.common.MockDataProvider
import com.saborexpress.databinding.FragmentBuyerCatalogBinding

/**
 * Fragmento de Catálogo para el Rol Comprador.
 * Muestra el listado de platos gastronómicos y permite filtrar en tiempo real.
 */
class BuyerCatalogFragment : Fragment() {

    private var _binding: FragmentBuyerCatalogBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: DishAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBuyerCatalogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSearch()
    }

    private fun setupRecyclerView() {
        adapter = DishAdapter(MockDataProvider.catalogDishes) { dish ->
            Toast.makeText(
                requireContext(),
                "¡${dish.name} añadido a la preorden!",
                Toast.LENGTH_SHORT
            ).show()
        }
        binding.rvDishesCatalog.adapter = adapter
    }

    private fun setupSearch() {
        binding.etSearchCatalog.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter(s?.toString().orEmpty(), MockDataProvider.catalogDishes)
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
