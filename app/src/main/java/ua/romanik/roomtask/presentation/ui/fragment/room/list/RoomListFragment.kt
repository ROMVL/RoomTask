package ua.romanik.roomtask.presentation.ui.fragment.room.list

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.PopupMenu
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_room_list.*
import kotlinx.android.synthetic.main.toolbar.*
import org.koin.android.viewmodel.ext.android.viewModel
import ua.romanik.roomtask.R
import ua.romanik.roomtask.domain.model.room.RoomDomainModel
import ua.romanik.roomtask.presentation.base.fragment.BaseFragment
import ua.romanik.roomtask.presentation.base.initText
import ua.romanik.roomtask.presentation.base.navigationevent.BaseNavigation
import ua.romanik.roomtask.presentation.base.visible
import ua.romanik.roomtask.presentation.ui.adapter.room.RoomAdapter
import ua.romanik.roomtask.presentation.ui.fragment.room.navigation.RoomNavigation


class RoomListFragment : BaseFragment<RoomListViewModel>(R.layout.fragment_room_list) {

    override val viewModel: RoomListViewModel by viewModel()
    private val roomAdapter by lazy {
        RoomAdapter(
            ::handleClickUpdateItem,
            ::handleClickDeleteItem
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRv()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(viewModel) {
            rooms.observe(
                this@RoomListFragment.viewLifecycleOwner,
                Observer { roomAdapter.submitList(it) }
            )
        }
    }

    override fun <T : BaseNavigation> handleNavigationEvent(navigationEvent: T) {
        fun navigateToCreateEditScreen(navEvent: RoomNavigation) {
            findNavController().navigate(
                RoomListFragmentDirections.actionRoomListFragmentToCreateRoomFragment(navEvent)
            )
        }
        when (navigationEvent) {
            is RoomNavigation.UpdateRoom -> {
                navigateToCreateEditScreen(navigationEvent)
            }
            is RoomNavigation.CreateRoom -> {
                navigateToCreateEditScreen(navigationEvent)
            }
        }
    }

    override fun initToolBar() {
        tvTitle.initText(R.string.rooms)
        with(btnMenu) {
            visible()
            setOnClickListener { showPopupMenu(it) }
        }
    }

    private fun showPopupMenu(anchor: View) {
        PopupMenu(anchor.context, anchor).apply {
            gravity = Gravity.END
            menuInflater.inflate(R.menu.menu_rooms, menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.itemCreate -> {
                        viewModel.onClickCreateRoom()
                        true
                    }
                    else -> false
                }
            }
        }.show()
    }

    private fun handleClickUpdateItem(roomDomainModel: RoomDomainModel) {
        viewModel.onClickUpdate(roomDomainModel)
    }

    private fun handleClickDeleteItem(roomDomainModel: RoomDomainModel) {
        viewModel.onClickDeleteRoom(roomDomainModel)
    }

    private fun initRv() {
        with(rvRooms) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = roomAdapter
        }
    }

}
