package com.saborexpress.buyer

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.saborexpress.databinding.FragmentBuyerProfileBinding

/**
 * Fragmento de Perfil de Usuario para el Rol Comprador (CRUD de Perfil).
 * Permite gestionar foto de perfil (simulación de cámara), datos de contacto,
 * preferencias dietéticas mediante Chips y visualización del método de pago seguro.
 */
class BuyerProfileFragment : Fragment() {

    private var _binding: FragmentBuyerProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBuyerProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        // Simulación de captura o cambio de foto con cámara / galería
        binding.btnChangeProfilePhoto.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Abriendo cámara/galería para actualizar fotografía de perfil...",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Editar método de pago guardado
        binding.tvChangePaymentMethod.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Gestionar métodos de pago guardados...",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Botón "Guardar Cambios"
        binding.btnSaveProfile.setOnClickListener {
            val name = binding.etFullName.text?.toString()?.trim().orEmpty()
            val email = binding.etProfileEmail.text?.toString()?.trim().orEmpty()
            val phone = binding.etProfilePhone.text?.toString()?.trim().orEmpty()
            val address = binding.etProfileAddress.text?.toString()?.trim().orEmpty()

            if (name.isEmpty()) {
                binding.tilFullName.error = "Ingresa tu nombre completo"
                binding.etFullName.requestFocus()
                return@setOnClickListener
            }
            binding.tilFullName.error = null

            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.tilProfileEmail.error = "Correo electrónico inválido"
                binding.etProfileEmail.requestFocus()
                return@setOnClickListener
            }
            binding.tilProfileEmail.error = null

            if (phone.isEmpty()) {
                binding.tilProfilePhone.error = "Ingresa tu número de teléfono"
                binding.etProfilePhone.requestFocus()
                return@setOnClickListener
            }
            binding.tilProfilePhone.error = null

            if (address.isEmpty()) {
                binding.tilProfileAddress.error = "Ingresa tu dirección"
                binding.etProfileAddress.requestFocus()
                return@setOnClickListener
            }
            binding.tilProfileAddress.error = null

            Toast.makeText(
                requireContext(),
                "Perfil de $name actualizado exitosamente.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

