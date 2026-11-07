package com.saborexpress.buyer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.saborexpress.databinding.FragmentBuyerHomeBinding

/**
 * Fragmento de Inicio para el Rol Comprador.
 * Muestra el mapa simulado con geolocalización, la sede sugerida más cercana
 * y los selectores de fecha, hora y comensales.
 */
class BuyerHomeFragment : Fragment() {

    private var _binding: FragmentBuyerHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBuyerHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

