package ua.romanik.roomtask.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ua.romanik.roomtask.data.db.dao.DepartmentDao
import ua.romanik.roomtask.data.db.dao.UserDao
import ua.romanik.roomtask.data.db.entity.department.DepartmentEntity
import ua.romanik.roomtask.data.db.entity.user.UserEntity
import ua.romanik.roomtask.data.db.entity.user.UserEntityWithInfo
import ua.romanik.roomtask.data.db.entity.user.UserInfoEntity

@Database(
    entities = [UserEntity::class, UserInfoEntity::class, DepartmentEntity::class],
    version = DepartmentDataBase.DB_VERSION,
    exportSchema = false
)
abstract class DepartmentDataBase : RoomDatabase() {

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "department_data_base"
    }

    abstract fun userDao(): UserDao

    abstract fun departmentDao(): DepartmentDao

}