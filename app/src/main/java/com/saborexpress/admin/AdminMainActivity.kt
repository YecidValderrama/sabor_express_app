package com.saborexpress.admin

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.saborexpress.R
import com.saborexpress.databinding.ActivityAdminMainBinding

/**
 * Actividad Principal para el Rol Administrador de SaborExpress.
 * Administra la navegación inferior entre:
 * - Usuarios (Estadísticas y gestión de clientes).
 * - Productos (CRUD de platos gastronómicos y disponibilidad).
 * - Reportes (Dashboard analítico).
 */
class AdminMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminMainBinding

    companion object {
        private const val TAG = "AdminMainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigation()

        if (savedInstanceState == null) {
            loadFragment(AdminUsersFragment(), "Usuarios")
        }
    }

    private fun setupBottomNavigation() {
        binding.adminBottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_admin_users -> {
                    Log.d(TAG, "Navegación: Usuarios seleccionado")
                    Toast.makeText(this, "Sección: Gestión de Usuarios", Toast.LENGTH_SHORT).show()
                    loadFragment(AdminUsersFragment(), "Usuarios")
                    true
                }
                R.id.nav_admin_products -> {
                    Log.d(TAG, "Navegación: Productos seleccionado")
                    Toast.makeText(this, "Sección: CRUD de Productos", Toast.LENGTH_SHORT).show()
                    loadFragment(AdminProductFormFragment(), "Productos")
                    true
                }
                R.id.nav_admin_reports -> {
                    Log.d(TAG, "Navegación: Reportes seleccionado")
                    Toast.makeText(this, "Sección: Reportes y Métricas", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment, title: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.adminFragmentContainer, fragment)
            .commit()
    }
}

