package ua.romanik.roomtask.presentation.ui.fragment.user.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.launch
import ua.romanik.roomtask.domain.model.user.UserDetailsWithDepartmentDomainModel
import ua.romanik.roomtask.domain.usecase.DeleteUserUseCase
import ua.romanik.roomtask.domain.usecase.FetchUserDetailsByIdUseCase
import ua.romanik.roomtask.presentation.base.livedata.Event
import ua.romanik.roomtask.presentation.base.viewmodel.BaseViewModel
import ua.romanik.roomtask.presentation.ui.fragment.user.navigation.UserNavigation

class UserDetailsViewModel(
    private val userId: Long,
    private val fetchUserDetailsByIdUseCase: FetchUserDetailsByIdUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
) : BaseViewModel() {

    private val _userDetails = fetchUserDetailsByIdUseCase.execute(userId)
        .asLiveData(viewModelScopeWithErrorHandler.coroutineContext)

    val userDetails: LiveData<UserDetailsWithDepartmentDomainModel> get() = _userDetails

    fun onClickUpdate() {
        _userDetails.value?.let {
            _navigationLiveEvent.value = Event(UserNavigation.UpdateUser(it.userDomainModel))
        }
    }

    fun onClickDelete() {
        viewModelScopeWithErrorHandler.launch {
            _userDetails.value?.let { user ->
                deleteUserUseCase.execute(user.userDomainModel)
            }
            _navigationLiveEvent.value = Event(UserNavigation.Back)
        }
    }

}