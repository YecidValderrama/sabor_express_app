package com.saborexpress.buyer

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.saborexpress.R
import com.saborexpress.databinding.ActivityBuyerMainBinding

/**
 * Actividad Principal para el Rol Comprador (Cliente).
 * Administra el BottomNavigationView con 4 secciones (Inicio, Menú, Preorden y Perfil)
 * y el contenedor FragmentContainerView.
 */
class BuyerMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBuyerMainBinding

    companion object {
        private const val TAG = "BuyerMainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuyerMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigation()

        // Cargar por defecto el fragmento de Inicio si es la primera creación
        if (savedInstanceState == null) {
            loadFragment(BuyerHomeFragment(), "Inicio")
        }
    }

    private fun setupBottomNavigation() {
        binding.buyerBottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_buyer_home -> {
                    Log.d(TAG, "Navegación: Inicio seleccionado")
                    Toast.makeText(this, "Sección: Inicio", Toast.LENGTH_SHORT).show()
                    loadFragment(BuyerHomeFragment(), "Inicio")
                    true
                }
                R.id.nav_buyer_catalog -> {
                    Log.d(TAG, "Navegación: Menú/Catálogo seleccionado")
                    Toast.makeText(this, "Sección: Menú / Catálogo", Toast.LENGTH_SHORT).show()
                    loadFragment(BuyerCatalogFragment(), "Catálogo")
                    true
                }
                R.id.nav_buyer_preorder -> {
                    Log.d(TAG, "Navegación: Preorden seleccionada")
                    Toast.makeText(this, "Sección: Preorden (Carrito con 10% de anticipo)", Toast.LENGTH_SHORT).show()
                    loadFragment(BuyerCartFragment(), "Preorden")
                    true
                }
                R.id.nav_buyer_profile -> {
                    Log.d(TAG, "Navegación: Perfil seleccionado")
                    Toast.makeText(this, "Sección: Perfil de Usuario", Toast.LENGTH_SHORT).show()
                    loadFragment(BuyerProfileFragment(), "Perfil")
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment, title: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.buyerFragmentContainer, fragment)
            .commit()
    }
}

