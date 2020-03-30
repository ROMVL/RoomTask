package ua.romanik.roomtask.di

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ua.romanik.roomtask.di.ViewModelPropertyKey.DEPARTMENT_ID
import ua.romanik.roomtask.di.ViewModelPropertyKey.USER_ID
import ua.romanik.roomtask.presentation.ui.activity.main.MainViewModel
import ua.romanik.roomtask.presentation.ui.fragment.department.create.CreateDepartmentViewModel
import ua.romanik.roomtask.presentation.ui.fragment.department.details.DepartmentDetailsViewModel
import ua.romanik.roomtask.presentation.ui.fragment.department.list.DepartmentListViewModel
import ua.romanik.roomtask.presentation.ui.fragment.room.create.CreateRoomViewModel
import ua.romanik.roomtask.presentation.ui.fragment.room.list.RoomListViewModel
import ua.romanik.roomtask.presentation.ui.fragment.user.create.CreateUserViewModel
import ua.romanik.roomtask.presentation.ui.fragment.user.details.UserDetailsViewModel
import ua.romanik.roomtask.presentation.ui.fragment.user.list.UsersListViewModel

@ExperimentalCoroutinesApi
val viewModelModule = module {
    viewModel { MainViewModel(get(), get(), get()) }
    viewModel { DepartmentListViewModel(get(), get()) }
    viewModel { UsersListViewModel(get(), get()) }
    viewModel { CreateDepartmentViewModel(get(), get()) }
    viewModel { DepartmentDetailsViewModel(getProperty(DEPARTMENT_ID), get(), get(), get()) }
    viewModel { UserDetailsViewModel(getProperty(USER_ID), get(), get()) }
    viewModel { CreateUserViewModel(get(), get(), get()) }
    viewModel { RoomListViewModel(get(), get()) }
    viewModel { CreateRoomViewModel(get(), get()) }
}

object ViewModelPropertyKey {
    const val DEPARTMENT_ID = "key_department_id"
    const val USER_ID = "key_user_id"
}