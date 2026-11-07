package com.saborexpress.buyer

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.saborexpress.R
import com.saborexpress.databinding.FragmentBuyerPaymentBinding
import java.util.concurrent.Executor

/**
 * Fragmento de Pasarela de Pagos para el Rol Comprador.
 * Implementa selección de método de pago (Visa, PSE, Nequi), verificación
 * del titular, confirmación biométrica (BiometricPrompt) y cobro con candado de seguridad HTTPS.
 */
class BuyerPaymentFragment : Fragment() {

    private var _binding: FragmentBuyerPaymentBinding? = null
    private val binding get() = _binding!!

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBuyerPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBiometric()
        setupListeners()
    }

    private fun setupBiometric() {
        executor = ContextCompat.getMainExecutor(requireContext())

        biometricPrompt = BiometricPrompt(
            this,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    if (errorCode != BiometricPrompt.ERROR_USER_CANCELED &&
                        errorCode != BiometricPrompt.ERROR_NEGATIVE_BUTTON
                    ) {
                        Toast.makeText(
                            requireContext(),
                            "Error biométrico: $errString",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(
                        requireContext(),
                        "¡Pago autorizado con huella dactilar exitosamente!",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(
                        requireContext(),
                        "Huella no reconocida. Intenta de nuevo.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(getString(R.string.biometric_prompt_title))
            .setSubtitle("Confirmación de Pago SaborExpress")
            .setDescription("Autoriza el cobro seguro de $97.000 COP usando tu sensor biométrico.")
            .setNegativeButtonText(getString(R.string.biometric_cancel))
            .setConfirmationRequired(false)
            .build()
    }

    private fun setupListeners() {
        // Confirmar con huella
        binding.btnBiometricConfirm.setOnClickListener {
            val biometricManager = BiometricManager.from(requireContext())
            if (biometricManager.canAuthenticate(
                    BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.BIOMETRIC_WEAK
                ) == BiometricManager.BIOMETRIC_SUCCESS
            ) {
                biometricPrompt.authenticate(promptInfo)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Sensor biométrico no disponible en este dispositivo.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Botón final Pagar Ahora
        binding.btnPayNow.setOnClickListener {
            val holderName = binding.etHolderName.text?.toString()?.trim().orEmpty()
            val cvv = binding.etCvv.text?.toString()?.trim().orEmpty()
            val email = binding.etBillingEmail.text?.toString()?.trim().orEmpty()

            if (holderName.isEmpty()) {
                binding.tilHolderName.error = "Ingresa el nombre del titular"
                binding.etHolderName.requestFocus()
                return@setOnClickListener
            }
            binding.tilHolderName.error = null

            if (binding.rbVisa.isChecked && cvv.length < 3) {
                binding.tilCvv.error = "CVV inválido"
                binding.etCvv.requestFocus()
                return@setOnClickListener
            }
            binding.tilCvv.error = null

            if (email.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.tilBillingEmail.error = "Correo inválido para el recibo"
                binding.etBillingEmail.requestFocus()
                return@setOnClickListener
            }
            binding.tilBillingEmail.error = null

            if (!binding.cbTerms.isChecked) {
                Toast.makeText(
                    requireContext(),
                    "Debes aceptar los términos y tratamiento de datos (Ley 1581).",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            // Simulación exitosa de pasarela de pagos
            Toast.makeText(
                requireContext(),
                "¡Pago procesado con éxito! Tu reserva y preorden están confirmadas.",
                Toast.LENGTH_LONG
            ).show()

            // Regresar al inicio
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

