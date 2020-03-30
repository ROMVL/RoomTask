package ua.romanik.roomtask.presentation.ui.fragment.department.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.launch
import ua.romanik.roomtask.domain.model.department.DepartmentDomainModel
import ua.romanik.roomtask.domain.model.room.RoomDomainModel
import ua.romanik.roomtask.domain.usecase.CreateDepartmentUseCase
import ua.romanik.roomtask.domain.usecase.FetchRoomsUseCase
import ua.romanik.roomtask.domain.usecase.UpdateDepartmentUseCase
import ua.romanik.roomtask.presentation.base.livedata.Event
import ua.romanik.roomtask.presentation.base.viewmodel.BaseViewModel
import ua.romanik.roomtask.presentation.ui.fragment.department.navigation.DepartmentNavigation

class CreateDepartmentViewModel(
    fetchRoomsUseCase: FetchRoomsUseCase,
    private val createDepartmentUseCase: CreateDepartmentUseCase,
    private val updateDepartmentUseCase: UpdateDepartmentUseCase
) : BaseViewModel() {

    private val _rooms =
        fetchRoomsUseCase.execute().asLiveData(viewModelScopeWithErrorHandler.coroutineContext)

    val rooms: LiveData<List<RoomDomainModel>> get() = _rooms

    fun onClickCreate(name: String, description: String, selectedRoomPosition: Int) {
        viewModelScopeWithErrorHandler.launch {
            _rooms.value?.let { values ->
                val selectedRoomId = values[selectedRoomPosition].id
                createDepartmentUseCase.execute(
                    DepartmentDomainModel(
                        name = name,
                        description = description,
                        roomId = selectedRoomId
                    )
                )
                _navigationLiveEvent.value = Event(DepartmentNavigation.Back)
            }
        }
    }

    fun onClickUpdate(id: Long, name: String, description: String, selectedRoomPosition: Int) {
        viewModelScopeWithErrorHandler.launch {
            _rooms.value?.let { values ->
                val selectedRoomId = values[selectedRoomPosition].id
                updateDepartmentUseCase.execute(
                    DepartmentDomainModel(
                        id = id,
                        name = name,
                        description = description,
                        roomId = selectedRoomId
                    )
                )
                _navigationLiveEvent.value = Event(DepartmentNavigation.Back)
            }
        }
    }

}