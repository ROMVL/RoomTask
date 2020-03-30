package ua.romanik.roomtask.presentation.ui.fragment.room.create

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_create_room.*
import kotlinx.android.synthetic.main.toolbar.*
import org.koin.android.viewmodel.ext.android.viewModel
import ua.romanik.roomtask.R
import ua.romanik.roomtask.presentation.base.fragment.BaseFragment
import ua.romanik.roomtask.presentation.base.initText
import ua.romanik.roomtask.presentation.base.navigationevent.BaseNavigation
import ua.romanik.roomtask.presentation.base.visible
import ua.romanik.roomtask.presentation.ui.fragment.room.navigation.RoomNavigation


class CreateRoomFragment : BaseFragment<CreateRoomViewModel>(R.layout.fragment_create_room) {

    override val viewModel: CreateRoomViewModel by viewModel()
    private val args by navArgs<CreateRoomFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when (args.navigationEvent) {
            is RoomNavigation.CreateRoom -> {
                tvTitle.initText(R.string.create_room)
                btnDone.setOnClickListener {
                    viewModel.onClickCreateRoom(
                        roomNumber = etRoomNumber.text.toString()
                    )
                }
            }
            is RoomNavigation.UpdateRoom -> {
                tvTitle.initText(R.string.update_room)
                with((args.navigationEvent as RoomNavigation.UpdateRoom).roomDomainModel) {
                    etRoomNumber.initText(number)
                    btnDone.setOnClickListener {
                        viewModel.onClickUpdateRoom(
                            roomId = id,
                            roomNumber = etRoomNumber.text.toString()
                        )
                    }
                }
            }
        }
    }

    override fun <T : BaseNavigation> handleNavigationEvent(navigationEvent: T) {
        when (navigationEvent) {
            RoomNavigation.Back -> findNavController().popBackStack()
        }
    }

    override fun initToolBar() {
        with(btnBackArrow) {
            visible()
            setOnClickListener { findNavController().popBackStack() }
        }
    }

}
