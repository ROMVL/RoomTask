package ua.romanik.roomtask.presentation.ui.adapter.user

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_user.view.*
import ua.romanik.roomtask.R
import ua.romanik.roomtask.domain.model.user.UserDomainModel
import ua.romanik.roomtask.presentation.base.initText

class UserAdapter(
    private val itemClickListener: (UserDomainModel) -> Unit,
    private val itemUpdateClickListener: (UserDomainModel) -> Unit,
    private val itemDeleteClickListener: (UserDomainModel) -> Unit
) : ListAdapter<UserDomainModel, UserAdapter.UserViewHolder>(userDiffUtil) {

    companion object {
        private val userDiffUtil = object : DiffUtil.ItemCallback<UserDomainModel>() {
            override fun areItemsTheSame(
                oldItem: UserDomainModel,
                newItem: UserDomainModel
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: UserDomainModel,
                newItem: UserDomainModel
            ): Boolean = oldItem.name == newItem.name
                    && oldItem.email == newItem.email
                    && oldItem.address == newItem.address
                    && oldItem.phone == newItem.phone

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(userDomainModel: UserDomainModel) {
            with(itemView) {
                setOnClickListener { itemClickListener(userDomainModel) }
                tvId.initText(userDomainModel.id)
                tvEmail.initText(userDomainModel.email)
                tvName.initText(userDomainModel.name)
                tvAddress.initText(userDomainModel.address)
                tvPhone.initText(userDomainModel.phone)
                btnMenu.setOnClickListener { showPopupMenu(it, userDomainModel) }
            }
        }

        private fun showPopupMenu(anchor: View, userDomainModel: UserDomainModel) {
            PopupMenu(anchor.context, anchor).apply {
                gravity = Gravity.END
                menuInflater.inflate(R.menu.menu_item, menu)
                setOnMenuItemClickListener {
                    when(it.itemId) {
                        R.id.itemUpdate -> {
                            itemUpdateClickListener(userDomainModel)
                            true
                        }
                        R.id.itemDelete -> {
                            itemDeleteClickListener(userDomainModel)
                            true
                        }
                        else -> false
                    }
                }
            }.show()
        }

    }

}