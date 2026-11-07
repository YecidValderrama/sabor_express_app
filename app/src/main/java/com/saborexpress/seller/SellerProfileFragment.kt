package com.saborexpress.seller

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.saborexpress.common.LoginActivity
import com.saborexpress.databinding.FragmentSellerProfileBinding

/**
 * Fragmento de Perfil para el Rol Vendedor.
 * Permite actualizar datos de contacto, simula el cambio de foto
 * y provee la opción de cerrar sesión para regresar al Login.
 */
class SellerProfileFragment : Fragment() {

    private var _binding: FragmentSellerProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSellerProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        binding.btnChangeSellerPhoto.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Abriendo cámara/galería para actualizar fotografía del vendedor...",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.btnSaveSellerProfile.setOnClickListener {
            val name = binding.etSellerName.text?.toString()?.trim().orEmpty()
            val email = binding.etSellerEmail.text?.toString()?.trim().orEmpty()
            val phone = binding.etSellerPhone.text?.toString()?.trim().orEmpty()

            if (name.isEmpty()) {
                binding.tilSellerName.error = "Ingresa tu nombre completo"
                binding.etSellerName.requestFocus()
                return@setOnClickListener
            }
            binding.tilSellerName.error = null

            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.tilSellerEmail.error = "Correo corporativo inválido"
                binding.etSellerEmail.requestFocus()
                return@setOnClickListener
            }
            binding.tilSellerEmail.error = null

            if (phone.isEmpty()) {
                binding.tilSellerPhone.error = "Ingresa tu teléfono"
                binding.etSellerPhone.requestFocus()
                return@setOnClickListener
            }
            binding.tilSellerPhone.error = null

            Toast.makeText(
                requireContext(),
                "Perfil de vendedor ($name) actualizado con éxito.",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.btnLogoutSeller.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Cerrando sesión de vendedor...",
                Toast.LENGTH_SHORT
            ).show()

            val intent = Intent(requireContext(), LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

