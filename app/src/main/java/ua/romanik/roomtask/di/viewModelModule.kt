package ua.romanik.roomtask.di

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ua.romanik.roomtask.presentation.ui.activity.main.MainViewModel
import ua.romanik.roomtask.presentation.ui.fragment.department.create.CreateDepartmentViewModel
import ua.romanik.roomtask.presentation.ui.fragment.department.list.DepartmentListViewModel
import ua.romanik.roomtask.presentation.ui.fragment.user.list.UsersListViewModel

@ExperimentalCoroutinesApi
val viewModelModule = module {
    viewModel { MainViewModel(get(), get()) }
    viewModel { DepartmentListViewModel(get(), get()) }
    viewModel { UsersListViewModel(get(), get()) }
    viewModel { CreateDepartmentViewModel(get(), get()) }
}