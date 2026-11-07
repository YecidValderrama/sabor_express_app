package com.saborexpress.admin

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.saborexpress.common.MockDataProvider
import com.saborexpress.databinding.FragmentAdminUsersBinding

/**
 * Fragmento de Administración de Usuarios con listado interactivo y buscador en tiempo real.
 */
class AdminUsersFragment : Fragment() {

    private var _binding: FragmentAdminUsersBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: UserAdapter
    private val currentUsers = MockDataProvider.usersList.toMutableList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSearch()
        setupListeners()
    }

    private fun setupRecyclerView() {
        adapter = UserAdapter(
            currentUsers,
            onEditClick = { user ->
                Toast.makeText(requireContext(), "Editar datos de: ${user.fullName}", Toast.LENGTH_SHORT).show()
            },
            onSuspendClick = { user ->
                user.isSuspended = !user.isSuspended
                val msg = if (user.isSuspended) "Cuenta suspendida: ${user.fullName}" else "Cuenta reactivada: ${user.fullName}"
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
            },
            onDeleteClick = { user, position ->
                currentUsers.removeAt(position)
                adapter.notifyItemRemoved(position)
                Toast.makeText(requireContext(), "Cuenta eliminada: ${user.fullName}", Toast.LENGTH_SHORT).show()
            }
        )
        binding.rvAdminUsers.adapter = adapter
    }

    private fun setupSearch() {
        binding.etSearchUsers.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter(s?.toString().orEmpty(), currentUsers)
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupListeners() {
        binding.btnCreateClient.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Formulario de creación de nuevo cliente (CRUD de Usuario)...",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
