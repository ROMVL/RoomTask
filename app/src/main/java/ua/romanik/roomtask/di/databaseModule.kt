package ua.romanik.roomtask.di

import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import ua.romanik.roomtask.data.db.DepartmentDataBase
import ua.romanik.roomtask.data.db.DepartmentDataBase.Companion.MIGRATION_1_2
import ua.romanik.roomtask.data.db.DepartmentDataBase.Companion.MIGRATION_2_3
import ua.romanik.roomtask.data.db.DepartmentDataBase.Companion.MIGRATION_3_4
import ua.romanik.roomtask.data.db.DepartmentDataBase.Companion.MIGRATION_4_5

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            DepartmentDataBase::class.java,
            DepartmentDataBase.DB_NAME
        ).addMigrations(
            MIGRATION_1_2,
            MIGRATION_2_3,
            MIGRATION_3_4,
            MIGRATION_4_5
        ).build()
    }
    single { get<DepartmentDataBase>().userDao() }
    single { get<DepartmentDataBase>().departmentDao() }
    single { get<DepartmentDataBase>().roomDao() }
}