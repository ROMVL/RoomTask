package ua.romanik.roomtask.presentation.ui.fragment.room.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.launch
import ua.romanik.roomtask.domain.model.room.RoomDomainModel
import ua.romanik.roomtask.domain.usecase.DeleteRoomUseCase
import ua.romanik.roomtask.domain.usecase.FetchRoomsUseCase
import ua.romanik.roomtask.presentation.base.livedata.Event
import ua.romanik.roomtask.presentation.base.viewmodel.BaseViewModel
import ua.romanik.roomtask.presentation.ui.fragment.room.navigation.RoomNavigation

class RoomListViewModel(
    fetchRoomsUseCase: FetchRoomsUseCase,
    private val deleteRoomUseCase: DeleteRoomUseCase
) : BaseViewModel() {

    private val _rooms = fetchRoomsUseCase.execute().asLiveData(viewModelScopeWithErrorHandler.coroutineContext)

    val rooms: LiveData<List<RoomDomainModel>> get() = _rooms

    fun onClickDeleteRoom(roomDomainModel: RoomDomainModel) {
        viewModelScopeWithErrorHandler.launch {
            deleteRoomUseCase.execute(roomDomainModel)
        }
    }

    fun onClickUpdate(roomDomainModel: RoomDomainModel) {
        _navigationLiveEvent.value = Event(RoomNavigation.UpdateRoom(roomDomainModel))
    }

    fun onClickCreateRoom() {
        _navigationLiveEvent.value = Event(RoomNavigation.CreateRoom)
    }

}