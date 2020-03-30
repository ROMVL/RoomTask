package ua.romanik.roomtask.presentation.ui.fragment.user.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.launch
import ua.romanik.roomtask.domain.model.department.DepartmentDomainModel
import ua.romanik.roomtask.domain.model.user.UserDomainModel
import ua.romanik.roomtask.domain.usecase.CreateUserUseCase
import ua.romanik.roomtask.domain.usecase.FetchDepartmentsUseCase
import ua.romanik.roomtask.domain.usecase.UpdateUserUseCase
import ua.romanik.roomtask.presentation.base.livedata.Event
import ua.romanik.roomtask.presentation.base.viewmodel.BaseViewModel
import ua.romanik.roomtask.presentation.ui.fragment.user.navigation.UserNavigation

class CreateUserViewModel(
    fetchDepartmentsUseCase: FetchDepartmentsUseCase,
    private val createUserUseCase: CreateUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : BaseViewModel() {

    private val _departments = fetchDepartmentsUseCase.execute()
        .asLiveData(viewModelScopeWithErrorHandler.coroutineContext)

    val departments: LiveData<List<DepartmentDomainModel>> get() = _departments

    fun onClickSaveUser(
        name: String,
        email: String,
        phone: String,
        selectedDepartmentPosition: Int
    ) {
        viewModelScopeWithErrorHandler.launch {
            _departments.value?.let {
                val selectedDepartment = it[selectedDepartmentPosition]
                createUserUseCase.execute(
                    UserDomainModel(
                        name = name,
                        email = email,
                        phone = phone,
                        departmentId = selectedDepartment.id
                    )
                )
                _navigationLiveEvent.value = Event(UserNavigation.Back)
            }
        }
    }

    fun onClickUpdateUser(
        userId: Long,
        name: String,
        email: String,
        phone: String,
        selectedDepartmentPosition: Int
    ) {
        viewModelScopeWithErrorHandler.launch {
            _departments.value?.let {
                val selectedDepartment = it[selectedDepartmentPosition]
                updateUserUseCase.execute(
                    UserDomainModel(
                        id = userId,
                        name = name,
                        email = email,
                        phone = phone,
                        departmentId = selectedDepartment.id
                    )
                )
                _navigationLiveEvent.value = Event(UserNavigation.Back)
            }
        }
    }

}