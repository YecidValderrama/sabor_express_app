package com.saborexpress.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.saborexpress.databinding.FragmentAdminProductFormBinding

/**
 * Fragmento de Formulario CRUD de Productos para el Rol Administrador.
 * Permite registrar y publicar platos gastronómicos con fotografía,
 * precio venta, tiempo de preparación, categoría en Spinner y estado de disponibilidad.
 */
class AdminProductFormFragment : Fragment() {

    private var _binding: FragmentAdminProductFormBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminProductFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCategorySpinner()
        setupListeners()
    }

    private fun setupCategorySpinner() {
        val categories = arrayOf(
            "Platos Fuertes y Cortes",
            "Entradas y Aperitivos",
            "Bebidas y Coctelería",
            "Postres Artesanales",
            "Especialidades del Chef"
        )
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            categories
        )
        binding.spinnerDishCategory.adapter = adapter
    }

    private fun setupListeners() {
        // Subir o tomar fotografía del plato
        binding.btnUploadDishPhoto.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Abriendo cámara o galería para capturar plato...",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Guardar Borrador
        binding.btnSaveDraft.setOnClickListener {
            val name = binding.etDishName.text?.toString()?.trim().orEmpty()
            if (name.isEmpty()) {
                binding.tilDishName.error = "Ingresa al menos el nombre del plato"
                binding.etDishName.requestFocus()
                return@setOnClickListener
            }
            binding.tilDishName.error = null

            Toast.makeText(
                requireContext(),
                "Borrador de '$name' guardado localmente.",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Guardar y Publicar
        binding.btnSaveAndPublish.setOnClickListener {
            val name = binding.etDishName.text?.toString()?.trim().orEmpty()
            val price = binding.etDishPrice.text?.toString()?.trim().orEmpty()
            val cookTime = binding.etDishCookTime.text?.toString()?.trim().orEmpty()
            val description = binding.etDishDescription.text?.toString()?.trim().orEmpty()

            if (name.isEmpty()) {
                binding.tilDishName.error = "Ingresa el nombre del plato"
                binding.etDishName.requestFocus()
                return@setOnClickListener
            }
            binding.tilDishName.error = null

            if (price.isEmpty()) {
                binding.tilDishPrice.error = "Ingresa el precio de venta"
                binding.etDishPrice.requestFocus()
                return@setOnClickListener
            }
            binding.tilDishPrice.error = null

            if (cookTime.isEmpty()) {
                binding.tilDishCookTime.error = "Ingresa el tiempo de cocina"
                binding.etDishCookTime.requestFocus()
                return@setOnClickListener
            }
            binding.tilDishCookTime.error = null

            if (description.isEmpty()) {
                binding.tilDishDescription.error = "Ingresa la descripción de ingredientes"
                binding.etDishDescription.requestFocus()
                return@setOnClickListener
            }
            binding.tilDishDescription.error = null

            val isAvailable = binding.switchDishAvailable.isChecked
            val category = binding.spinnerDishCategory.selectedItem.toString()

            Toast.makeText(
                requireContext(),
                "¡Plato '$name' publicado exitosamente en $category! (Disponible: $isAvailable)",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

