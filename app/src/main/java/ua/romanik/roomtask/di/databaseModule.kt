package ua.romanik.roomtask.di

import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import ua.romanik.roomtask.data.db.DepartmentDataBase

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            DepartmentDataBase::class.java,
            DepartmentDataBase.DB_NAME
        ).build()
    }
    single { get<DepartmentDataBase>().userDao() }
    single { get<DepartmentDataBase>().departmentDao() }
}