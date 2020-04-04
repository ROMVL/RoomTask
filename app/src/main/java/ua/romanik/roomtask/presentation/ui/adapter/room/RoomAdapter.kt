package ua.romanik.roomtask.presentation.ui.adapter.room

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_room.view.*
import ua.romanik.roomtask.R
import ua.romanik.roomtask.domain.model.room.RoomDomainModel
import ua.romanik.roomtask.presentation.base.initText

class RoomAdapter(
    private val updateOnClickListener: (RoomDomainModel) -> Unit,
    private val deleteOnClickListener: (RoomDomainModel) -> Unit
) : ListAdapter<RoomDomainModel, RoomAdapter.RoomViewHolder>(diffUtilItemCallback) {

    companion object {
        val diffUtilItemCallback = object : DiffUtil.ItemCallback<RoomDomainModel>() {
            override fun areItemsTheSame(
                oldItem: RoomDomainModel,
                newItem: RoomDomainModel
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: RoomDomainModel,
                newItem: RoomDomainModel
            ): Boolean = oldItem.number == newItem.number
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        return RoomViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_room, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class RoomViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun onBind(roomDomainModel: RoomDomainModel) {
            with(itemView) {
                tvRoomNumber.initText(roomDomainModel.number)
                btnMenu.setOnClickListener { showPopupMenu(it, roomDomainModel) }
            }
        }

        private fun showPopupMenu(anchor: View, roomDomainModel: RoomDomainModel) {
            PopupMenu(anchor.context, anchor).apply {
                gravity = Gravity.END
                menuInflater.inflate(R.menu.menu_item, menu)
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.itemUpdate -> {
                            updateOnClickListener(roomDomainModel)
                            true
                        }
                        R.id.itemDelete -> {
                            deleteOnClickListener(roomDomainModel)
                            true
                        }
                        else -> false
                    }
                }
            }.show()
        }

    }

}