package ua.romanik.roomtask.presentation.ui.fragment.department.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import ua.romanik.roomtask.domain.model.department.DepartmentDomainModel
import ua.romanik.roomtask.domain.usecase.DeleteDepartmentUseCase
import ua.romanik.roomtask.domain.usecase.FetchDepartmentsUseCase
import ua.romanik.roomtask.presentation.base.livedata.Event
import ua.romanik.roomtask.presentation.base.viewmodel.BaseViewModel
import ua.romanik.roomtask.presentation.ui.fragment.department.navigation.DepartmentNavigation

@ExperimentalCoroutinesApi
class DepartmentListViewModel(
    private val fetchDepartmentsUseCase: FetchDepartmentsUseCase,
    private val deleteDepartmentUseCase: DeleteDepartmentUseCase
) : BaseViewModel() {

    private val _departments = fetchDepartmentsUseCase
        .execute()
        .asLiveData(viewModelScopeWithErrorHandler.coroutineContext)

    val departments: LiveData<List<DepartmentDomainModel>>
        get() = _departments

    fun onClickCreate() {
        _navigationLiveEvent.value = Event(DepartmentNavigation.CreateDepartment)
    }

    fun onClickUpdate(domainModel: DepartmentDomainModel) {
        _navigationLiveEvent.value = Event(DepartmentNavigation.UpdateDepartment(domainModel))
    }

    fun onClickDeleteDepartment(departmentDomainModel: DepartmentDomainModel) {
        viewModelScopeWithErrorHandler.launch {
            deleteDepartmentUseCase.execute(departmentDomainModel)
        }
    }
}