package ua.romanik.roomtask.presentation.ui.adapter.department

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_department.view.*
import ua.romanik.roomtask.R
import ua.romanik.roomtask.domain.model.department.DepartmentDomainModel
import ua.romanik.roomtask.presentation.base.initText

class DepartmentAdapter(
    private val itemClickListener: (DepartmentDomainModel) -> Unit,
    private val itemDeleteClickListener: (DepartmentDomainModel) -> Unit,
    private val itemUpdateClickListener: (DepartmentDomainModel) -> Unit
) : ListAdapter<DepartmentDomainModel, DepartmentAdapter.DepartmentViewHolder>(
    departmentDiffCallback
) {

    companion object {
        private val departmentDiffCallback =
            object : DiffUtil.ItemCallback<DepartmentDomainModel>() {
                override fun areItemsTheSame(
                    oldItem: DepartmentDomainModel,
                    newItem: DepartmentDomainModel
                ): Boolean = oldItem.id == newItem.id

                override fun areContentsTheSame(
                    oldItem: DepartmentDomainModel,
                    newItem: DepartmentDomainModel
                ): Boolean =
                    oldItem.name == newItem.name && oldItem.description == newItem.description

            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartmentViewHolder {
        return DepartmentViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_department, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DepartmentViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class DepartmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(departmentDomainModel: DepartmentDomainModel) {
            with(itemView) {
                setOnClickListener { itemClickListener(departmentDomainModel) }
                tvId.initText(departmentDomainModel.id)
                tvName.initText(departmentDomainModel.name)
                tvDescription.initText(departmentDomainModel.description)
                btnMenu.setOnClickListener { showPopupMenu(it, departmentDomainModel) }
            }
        }

        private fun showPopupMenu(anchor: View, departmentDomainModel: DepartmentDomainModel) {
            PopupMenu(anchor.context, anchor).apply {
                gravity = Gravity.END
                menuInflater.inflate(R.menu.menu_item, menu)
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.itemUpdate -> {
                            itemUpdateClickListener(departmentDomainModel)
                            true
                        }
                        R.id.itemDelete -> {
                            itemDeleteClickListener(departmentDomainModel)
                            true
                        }
                        else -> false
                    }
                }
            }.show()
        }
    }

}