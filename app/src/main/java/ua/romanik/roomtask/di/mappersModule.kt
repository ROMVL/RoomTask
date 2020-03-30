package ua.romanik.roomtask.di

import org.koin.dsl.module
import ua.romanik.roomtask.domain.mapper.DepartmentMapper
import ua.romanik.roomtask.domain.mapper.RoomMapper
import ua.romanik.roomtask.domain.mapper.UserMapper

val domainMapperModule = module {
    single { UserMapper() }
    single { DepartmentMapper(get()) }
    single { RoomMapper() }
}