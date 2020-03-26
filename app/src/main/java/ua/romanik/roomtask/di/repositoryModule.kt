package ua.romanik.roomtask.di

import org.koin.dsl.module
import ua.romanik.roomtask.data.repository.DepartmentRepositoryImpl
import ua.romanik.roomtask.data.repository.UserRepositoryImpl
import ua.romanik.roomtask.domain.repository.DepartmentRepository
import ua.romanik.roomtask.domain.repository.UserRepository

val repositoryModule = module {
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<DepartmentRepository> { DepartmentRepositoryImpl(get()) }
}