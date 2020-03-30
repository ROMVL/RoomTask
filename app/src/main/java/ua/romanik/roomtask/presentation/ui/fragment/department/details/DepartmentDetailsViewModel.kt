package ua.romanik.roomtask.presentation.ui.fragment.department.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.core.inject
import ua.romanik.roomtask.domain.mapper.DepartmentMapper
import ua.romanik.roomtask.domain.model.department.DepartmentWithStuffAndRoomDomainModel
import ua.romanik.roomtask.domain.model.department.DepartmentWithStuffDomainModel
import ua.romanik.roomtask.domain.model.user.UserDomainModel
import ua.romanik.roomtask.domain.usecase.DeleteDepartmentUseCase
import ua.romanik.roomtask.domain.usecase.DeleteUserUseCase
import ua.romanik.roomtask.domain.usecase.FetchDepartmentByIdWithStuff
import ua.romanik.roomtask.presentation.base.livedata.Event
import ua.romanik.roomtask.presentation.base.viewmodel.BaseViewModel
import ua.romanik.roomtask.presentation.ui.fragment.department.navigation.DepartmentNavigation
import ua.romanik.roomtask.presentation.ui.fragment.user.navigation.UserNavigation

class DepartmentDetailsViewModel(
    private val departmentId: Long,
    private val fetchDepartmentByIdWithStuff: FetchDepartmentByIdWithStuff,
    private val deleteDepartmentUseCase: DeleteDepartmentUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
) : BaseViewModel() {

    private val departmentMapper by inject<DepartmentMapper>()

    private val _departmentWithStuff by lazy {
        fetchDepartmentByIdWithStuff.execute(departmentId)
            .asLiveData(viewModelScopeWithErrorHandler.coroutineContext)
    }

    val departmentWithStuff: LiveData<DepartmentWithStuffAndRoomDomainModel> get() = _departmentWithStuff

    fun onClickUpdate() {
        viewModelScopeWithErrorHandler.launch {
            _departmentWithStuff.value?.let { departmentWithStuffDomainModel ->
                _navigationLiveEvent.value = Event(
                    DepartmentNavigation.UpdateDepartment(
                        departmentMapper
                            .mapDepartmentWithStuffAndRoomDomainModelToDepartmentDomainModel(
                                departmentWithStuffDomainModel
                            )
                    )
                )
            }
        }
    }

    fun onClickDelete() {
        viewModelScopeWithErrorHandler.launch {
            _departmentWithStuff.value?.let { departmentWithStuffDomainModel ->
                deleteDepartmentUseCase.execute(
                    departmentMapper.mapDepartmentWithStuffAndRoomDomainModelToDepartmentDomainModel(
                        departmentWithStuffDomainModel
                    )
                )
                _navigationLiveEvent.value = Event(DepartmentNavigation.Back)
            }
        }
    }

    fun onClickUpdateUser(userDomainModel: UserDomainModel) {
        _navigationLiveEvent.value = Event(UserNavigation.UpdateUser(userDomainModel))
    }

    fun onClickDeleteUser(userDomainModel: UserDomainModel) {
        viewModelScopeWithErrorHandler.launch {
            deleteUserUseCase.execute(userDomainModel)
        }
    }

    fun onClickItemUser(userDomainModel: UserDomainModel) {
        _navigationLiveEvent.value = Event(UserNavigation.Details(userDomainModel.id))
    }

}