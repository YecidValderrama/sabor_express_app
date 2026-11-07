package com.saborexpress.seller

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.saborexpress.R
import com.saborexpress.databinding.ActivitySellerMainBinding

/**
 * Actividad Principal para el Rol Vendedor (Gestión de Ventas y Cocina).
 * Administra la navegación inferior entre:
 * - Pedidos (Pase de Cocina).
 * - Productos (Inventario / Catálogo de platos de la sede).
 * - Perfil (Información del empleado y cierre de sesión).
 */
class SellerMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySellerMainBinding

    companion object {
        private const val TAG = "SellerMainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellerMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigation()

        if (savedInstanceState == null) {
            loadFragment(SellerDashboardFragment(), "Pedidos")
        }
    }

    private fun setupBottomNavigation() {
        binding.sellerBottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_seller_orders -> {
                    Log.d(TAG, "Navegación: Pedidos / Pase de Cocina seleccionado")
                    Toast.makeText(this, "Sección: Pase de Cocina", Toast.LENGTH_SHORT).show()
                    loadFragment(SellerDashboardFragment(), "Pedidos")
                    true
                }
                R.id.nav_seller_products -> {
                    Log.d(TAG, "Navegación: Productos seleccionados")
                    Toast.makeText(this, "Sección: Catálogo de Productos", Toast.LENGTH_SHORT).show()
                    loadFragment(SellerProductsFragment(), "Productos")
                    true
                }
                R.id.nav_seller_profile -> {
                    Log.d(TAG, "Navegación: Perfil Vendedor seleccionado")
                    Toast.makeText(this, "Sección: Perfil de Vendedor", Toast.LENGTH_SHORT).show()
                    loadFragment(SellerProfileFragment(), "Perfil")
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment, title: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.sellerFragmentContainer, fragment)
            .commit()
    }
}

