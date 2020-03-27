package ua.romanik.roomtask.presentation.ui.fragment.user.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.launch
import ua.romanik.roomtask.domain.model.user.UserDomainModel
import ua.romanik.roomtask.domain.usecase.DeleteUserUseCase
import ua.romanik.roomtask.domain.usecase.FetchUsersUseCase
import ua.romanik.roomtask.presentation.base.livedata.Event
import ua.romanik.roomtask.presentation.base.viewmodel.BaseViewModel
import ua.romanik.roomtask.presentation.ui.fragment.user.navigation.UserNavigation

class UsersListViewModel(
    private val fetchUsersUseCase: FetchUsersUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
) : BaseViewModel() {

    private val _users = fetchUsersUseCase.execute().asLiveData(viewModelScopeWithErrorHandler.coroutineContext)
    val users: LiveData<List<UserDomainModel>>
        get() = _users

    fun onClickCreate() {
        _navigationLiveEvent.value = Event(UserNavigation.CreateUser)
    }

    fun onClickUpdate(userDomainModel: UserDomainModel) {
        _navigationLiveEvent.value = Event(UserNavigation.UpdateUser(userDomainModel))
    }

    fun onClickDelete(userDomainModel: UserDomainModel) {
        viewModelScopeWithErrorHandler.launch {
            deleteUserUseCase.execute(userDomainModel)
        }
    }

    fun onClickUser(userDomainModel: UserDomainModel) {
        _navigationLiveEvent.value = Event(UserNavigation.Details(userDomainModel.id))
    }

}