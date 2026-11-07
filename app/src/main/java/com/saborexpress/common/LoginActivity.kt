package com.saborexpress.common

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.saborexpress.R
import com.saborexpress.admin.AdminMainActivity
import com.saborexpress.buyer.BuyerMainActivity
import com.saborexpress.databinding.ActivityLoginBinding
import com.saborexpress.seller.SellerMainActivity
import java.util.concurrent.Executor

/**
 * Pantalla de inicio de sesión de SaborExpress.
 * Maneja autenticación tradicional (correo/contraseña),
 * enlace de recuperación, Google Sign-In simulado
 * y autenticación biométrica real con AndroidX BiometricPrompt.
 */
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBiometricAuth()
        setupListeners()
    }

    private fun setupBiometricAuth() {
        executor = ContextCompat.getMainExecutor(this)

        biometricPrompt = BiometricPrompt(
            this,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    // Si el usuario cancela manualmente, no mostramos error invasivo
                    if (errorCode != BiometricPrompt.ERROR_USER_CANCELED &&
                        errorCode != BiometricPrompt.ERROR_NEGATIVE_BUTTON
                    ) {
                        Toast.makeText(
                            this@LoginActivity,
                            "Error de autenticación biométrica: $errString",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(
                        this@LoginActivity,
                        "¡Bienvenido a SaborExpress! (Autenticado con Huella - Rol Comprador)",
                        Toast.LENGTH_LONG
                    ).show()
                    val intent = Intent(this@LoginActivity, BuyerMainActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(
                        this@LoginActivity,
                        "Huella no reconocida. Intenta nuevamente.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(getString(R.string.biometric_prompt_title))
            .setSubtitle(getString(R.string.biometric_prompt_subtitle))
            .setDescription(getString(R.string.biometric_prompt_desc))
            .setNegativeButtonText(getString(R.string.biometric_cancel))
            .setConfirmationRequired(false)
            .build()
    }

    private fun setupListeners() {
        // Botón principal "Ingresar al Menú"
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text?.toString()?.trim().orEmpty()
            val password = binding.etPassword.text?.toString()?.trim().orEmpty()

            if (email.isEmpty()) {
                binding.tilEmail.error = "Por favor ingresa tu correo"
                binding.etEmail.requestFocus()
                return@setOnClickListener
            }
            binding.tilEmail.error = null

            if (password.isEmpty()) {
                binding.tilPassword.error = "Por favor ingresa tu contraseña"
                binding.etPassword.requestFocus()
                return@setOnClickListener
            }
            binding.tilPassword.error = null

            // Redirección según rol (Demo / Mock)
            val cleanEmail = email.lowercase()
            when {
                cleanEmail.contains("admin") -> {
                    Toast.makeText(this, "Bienvenido Administrador", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, AdminMainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                cleanEmail.contains("vendedor") || cleanEmail.contains("seller") || cleanEmail.contains("cocina") -> {
                    Toast.makeText(this, "Bienvenido Vendedor / Cocina", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, SellerMainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else -> {
                    // Por defecto, cualquier otro correo ingresa como Comprador/Cliente
                    Toast.makeText(this, "Bienvenido Comprador: $email", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, BuyerMainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }

        // Botones de acceso directo rápido (Demo)
        binding.btnDemoBuyer.setOnClickListener {
            binding.etEmail.setText("cliente@saborexpress.com")
            binding.etPassword.setText("123456")
            val intent = Intent(this, BuyerMainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnDemoSeller.setOnClickListener {
            binding.etEmail.setText("vendedor@saborexpress.com")
            binding.etPassword.setText("123456")
            val intent = Intent(this, SellerMainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnDemoAdmin.setOnClickListener {
            binding.etEmail.setText("admin@saborexpress.com")
            binding.etPassword.setText("123456")
            val intent = Intent(this, AdminMainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // ImageButton gigante de Huella Dactilar
        binding.btnBiometricFingerprint.setOnClickListener {
            checkAndLaunchBiometrics()
        }

        // Enlace "¿Olvidaste tu contraseña?"
        binding.tvForgotPassword.setOnClickListener {
            val intent = Intent(this, RecoveryActivity::class.java)
            startActivity(intent)
        }

        // Botón Google Sign-In
        binding.btnGoogleSignIn.setOnClickListener {
            Toast.makeText(
                this,
                "Conectando con Google / Firebase...",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Botón "Crear cuenta nueva"
        binding.btnCreateAccount.setOnClickListener {
            Toast.makeText(
                this,
                "Módulo de registro en desarrollo (Rol Comprador)",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun checkAndLaunchBiometrics() {
        val biometricManager = BiometricManager.from(this)
        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.BIOMETRIC_WEAK)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                biometricPrompt.authenticate(promptInfo)
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                Toast.makeText(
                    this,
                    "Este dispositivo no cuenta con sensor biométrico.",
                    Toast.LENGTH_LONG
                ).show()
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                Toast.makeText(
                    this,
                    "El sensor biométrico no está disponible en este momento.",
                    Toast.LENGTH_LONG
                ).show()
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                Toast.makeText(
                    this,
                    "No hay huellas registradas en el dispositivo. Configúralas en Ajustes.",
                    Toast.LENGTH_LONG
                ).show()
            }
            else -> {
                Toast.makeText(
                    this,
                    "Autenticación biométrica no soportada.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

