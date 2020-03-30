package ua.romanik.roomtask.di

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.module
import ua.romanik.roomtask.domain.usecase.*

@ExperimentalCoroutinesApi
val useCaseModule = module {
    single<FetchDepartmentsUseCase> { FetchDepartmentsUseCaseImpl(get()) }
    single<FetchUsersUseCase> { FetchUsersUseCaseImpl(get()) }
    single<DeleteDepartmentUseCase> { DeleteDepartmentUseCaseImpl(get()) }
    single<DeleteUserUseCase> { DeleteUserUseCaseImpl(get()) }
    single<CreateDepartmentUseCase> { CreateDepartmentUseCaseImpl(get()) }
    single<UpdateDepartmentUseCase> { UpdateDepartmentUseCaseImpl(get()) }
    single<FetchDepartmentByIdWithStuff> { FetchDepartmentByIdWithStuffImpl(get()) }
    single<FetchUserDetailsByIdUseCase> { FetchUserDetailsByIdUseCaseImpl(get()) }
    single<CreateUserUseCase> { CreateUserUseCaseImpl(get()) }
    single<UpdateUserUseCase> { UpdateUserUseCaseImpl(get()) }
    single<FetchRoomsUseCase> { FetchRoomsUseCaseImpl(get()) }
    single<DeleteRoomUseCase> { DeleteRoomUseCaseImpl(get()) }
    single<CreateRoomUseCase> { CreateRoomUseCaseImpl(get()) }
    single<UpdateRoomUseCase> { UpdateRoomUseCaseImpl(get()) }
}