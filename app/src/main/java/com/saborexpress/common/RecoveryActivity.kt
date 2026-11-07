package com.saborexpress.common

import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.saborexpress.databinding.ActivityRecoveryBinding

/**
 * Pantalla de recuperación de contraseña de SaborExpress.
 * Permite solicitar enlace de restablecimiento seguro vía correo electrónico.
 */
class RecoveryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecoveryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecoveryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        // Botón volver
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.tvBackToLogin.setOnClickListener {
            finish()
        }

        // Botón terracota para solicitar recuperación
        binding.btnSubmitRecovery.setOnClickListener {
            val email = binding.etRecoveryEmail.text?.toString()?.trim().orEmpty()

            if (email.isEmpty()) {
                binding.tilRecoveryEmail.error = "Ingresa tu correo registrado"
                binding.etRecoveryEmail.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.tilRecoveryEmail.error = "Formato de correo inválido"
                binding.etRecoveryEmail.requestFocus()
                return@setOnClickListener
            }

            binding.tilRecoveryEmail.error = null

            // Simulación de envío del correo de recuperación
            Toast.makeText(
                this,
                "Se ha enviado un correo a $email con instrucciones de restablecimiento.",
                Toast.LENGTH_LONG
            ).show()

            // Finalizar actividad tras solicitar
            finish()
        }
    }
}

