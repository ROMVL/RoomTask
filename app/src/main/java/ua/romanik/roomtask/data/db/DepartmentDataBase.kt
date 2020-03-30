package ua.romanik.roomtask.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
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
        const val DB_VERSION = 3
        const val DB_NAME = "department_data_base"

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                with(database) {
                    execSQL("CREATE TABLE new_user_info (userInfoId INTEGER NOT NULL, userId INTEGER NOT NULL, name_user TEXT NOT NULL, phone TEXT NOT NULL, PRIMARY KEY(userInfoId))")
                    execSQL("INSERT INTO new_user_info (userInfoId, userId, name_user, phone) SELECT userInfoId, userId, name_user, phone FROM UserInfoEntity")
                    execSQL("DROP TABLE UserInfoEntity")
                    execSQL("ALTER TABLE new_user_info RENAME TO UserInfoEntity")
                }
            }
        }

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                with(database) {
                    execSQL("CREATE TABLE new_user_info (userInfoId INTEGER NOT NULL, userId INTEGER NOT NULL, name_user TEXT NOT NULL, phone INTEGER NOT NULL, PRIMARY KEY(userInfoId))")
                    execSQL("INSERT INTO new_user_info (userInfoId, userId, name_user, phone) SELECT userInfoId, userId, name_user, phone FROM UserInfoEntity")
                    execSQL("DROP TABLE UserInfoEntity")
                    execSQL("ALTER TABLE new_user_info RENAME TO UserInfoEntity")
                }
            }
        }
    }

    abstract fun userDao(): UserDao

    abstract fun departmentDao(): DepartmentDao

}