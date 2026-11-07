package com.saborexpress.admin

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.saborexpress.common.UserItem
import com.saborexpress.databinding.ItemAdminUserBinding

class UserAdapter(
    private var users: MutableList<UserItem>,
    private val onEditClick: (UserItem) -> Unit,
    private val onSuspendClick: (UserItem) -> Unit,
    private val onDeleteClick: (UserItem, Int) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(val binding: ItemAdminUserBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemAdminUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        with(holder.binding) {
            tvAdminUserName.text = user.fullName
            tvAdminUserEmail.text = user.email
            tvAdminUserDoc.text = user.documentId
            tvAdminUserLastBooking.text = user.lastBooking

            chipUserRole.text = if (user.isSuspended) "SUSPENDIDO" else user.roleBadge

            btnSuspendUser.text = if (user.isSuspended) "Reactivar" else "Suspender"

            btnEditUser.setOnClickListener {
                onEditClick(user)
            }

            btnSuspendUser.setOnClickListener {
                onSuspendClick(user)
                notifyItemChanged(position)
            }

            btnDeleteUser.setOnClickListener {
                val currentPos = holder.adapterPosition
                if (currentPos != RecyclerView.NO_POSITION) {
                    onDeleteClick(user, currentPos)
                }
            }
        }
    }

    override fun getItemCount(): Int = users.size

    fun filter(query: String, fullList: List<UserItem>) {
        users = if (query.isEmpty()) {
            fullList.toMutableList()
        } else {
            fullList.filter {
                it.fullName.contains(query, ignoreCase = true) ||
                it.email.contains(query, ignoreCase = true) ||
                it.documentId.contains(query, ignoreCase = true)
            }.toMutableList()
        }
        notifyDataSetChanged()
    }
}
