package ua.romanik.roomtask.presentation.ui.fragment.room.create

import kotlinx.coroutines.launch
import ua.romanik.roomtask.domain.model.room.RoomDomainModel
import ua.romanik.roomtask.domain.usecase.CreateRoomUseCase
import ua.romanik.roomtask.domain.usecase.UpdateRoomUseCase
import ua.romanik.roomtask.presentation.base.livedata.Event
import ua.romanik.roomtask.presentation.base.viewmodel.BaseViewModel
import ua.romanik.roomtask.presentation.ui.fragment.room.navigation.RoomNavigation

class CreateRoomViewModel(
    private val createRoomUseCase: CreateRoomUseCase,
    private val updateRoomUseCase: UpdateRoomUseCase
) : BaseViewModel() {

    fun onClickCreateRoom(roomNumber: String) {
        viewModelScopeWithErrorHandler.launch {
            createRoomUseCase.execute(RoomDomainModel(number = roomNumber.toLong()))
            _navigationLiveEvent.value = Event(RoomNavigation.Back)
        }
    }

    fun onClickUpdateRoom(roomId: Long, roomNumber: String) {
        viewModelScopeWithErrorHandler.launch {
            updateRoomUseCase.execute(
                RoomDomainModel(
                    id = roomId,
                    number = roomNumber.toLong()
                )
            )
            _navigationLiveEvent.value = Event(RoomNavigation.Back)
        }
    }

}