package com.saborexpress.buyer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.saborexpress.R
import com.saborexpress.common.MockDataProvider
import com.saborexpress.databinding.FragmentBuyerCartBinding

/**
 * Fragmento de Carrito / Preorden gastronómica para el Rol Comprador.
 * Calcula en tiempo real Subtotal, Anticipo de reserva (10%) y Total a Preordenar.
 */
class BuyerCartFragment : Fragment() {

    private var _binding: FragmentBuyerCartBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBuyerCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCartList()
        updateTotals()
        setupListeners()
    }

    private fun setupCartList() {
        adapter = CartAdapter(MockDataProvider.initialCartDishes) {
            updateTotals()
        }
        binding.rvCartDishes.adapter = adapter
    }

    private fun updateTotals() {
        val subtotal = MockDataProvider.initialCartDishes.sumOf { it.price * it.quantity }
        val deposit = subtotal * 0.10
        val total = subtotal

        binding.tvSummarySubtotal.text = String.format("$ %,d COP", subtotal.toLong()).replace(',', '.')
        binding.tvSummaryDeposit.text = String.format("$ %,d COP", deposit.toLong()).replace(',', '.')
        binding.tvSummaryTotal.text = String.format("$ %,d COP", total.toLong()).replace(',', '.')
    }

    private fun setupListeners() {
        binding.btnProceedToPayment.setOnClickListener {
            if (MockDataProvider.initialCartDishes.isEmpty()) {
                Toast.makeText(requireContext(), "Tu preorden está vacía.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            parentFragmentManager.beginTransaction()
                .replace(R.id.buyerFragmentContainer, BuyerPaymentFragment())
                .addToBackStack("CartToPayment")
                .commit()
            Toast.makeText(requireContext(), "Iniciando pasarela de pagos segura...", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
