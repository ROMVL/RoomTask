package ua.romanik.roomtask.presentation.ui.fragment.department.create

import kotlinx.coroutines.launch
import ua.romanik.roomtask.domain.model.department.DepartmentDomainModel
import ua.romanik.roomtask.domain.usecase.CreateDepartmentUseCase
import ua.romanik.roomtask.domain.usecase.UpdateDepartmentUseCase
import ua.romanik.roomtask.presentation.base.livedata.Event
import ua.romanik.roomtask.presentation.base.viewmodel.BaseViewModel
import ua.romanik.roomtask.presentation.ui.fragment.department.navigation.DepartmentNavigation

class CreateDepartmentViewModel(
    private val createDepartmentUseCase: CreateDepartmentUseCase,
    private val updateDepartmentUseCase: UpdateDepartmentUseCase
) : BaseViewModel() {

    fun onClickCreate(name: String, description: String) {
        viewModelScopeWithErrorHandler.launch {
            createDepartmentUseCase
                .execute(DepartmentDomainModel(name = name, description = description))
            _navigationLiveEvent.value = Event(DepartmentNavigation.Back)
        }
    }

    fun onClickUpdate(id: Long, name: String, description: String) {
        viewModelScopeWithErrorHandler.launch {
            updateDepartmentUseCase
                .execute(DepartmentDomainModel(id = id, name = name, description = description))
            _navigationLiveEvent.value = Event(DepartmentNavigation.Back)
        }
    }

}