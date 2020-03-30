package ua.romanik.roomtask.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import ua.romanik.roomtask.data.db.dao.DepartmentDao
import ua.romanik.roomtask.data.db.dao.RoomDao
import ua.romanik.roomtask.data.db.dao.UserDao
import ua.romanik.roomtask.data.db.entity.department.DepartmentEntity
import ua.romanik.roomtask.data.db.entity.room.RoomEntity
import ua.romanik.roomtask.data.db.entity.user.UserEntity
import ua.romanik.roomtask.data.db.entity.user.UserInfoEntity

@Database(
    entities = [
        UserEntity::class,
        UserInfoEntity::class,
        DepartmentEntity::class,
        RoomEntity::class
    ],
    version = DepartmentDataBase.DB_VERSION,
    exportSchema = false
)
abstract class DepartmentDataBase : RoomDatabase() {

    companion object {
        const val DB_VERSION = 5
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

        val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                with(database) {
                    execSQL("CREATE TABLE DepartmentRoom (idRoom INTEGER NOT NULL, roomNumber INTEGER NOT NULL, PRIMARY KEY(idRoom))")
                }
            }
        }

        val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {
                with(database) {
                    execSQL("CREATE TABLE new_department (id_department INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name_department TEXT NOT NULL, description TEXT NOT NULL, roomId INTEGER, FOREIGN KEY(roomId) REFERENCES DepartmentRoom(idRoom) ON UPDATE NO ACTION ON DELETE SET NULL)")
                    execSQL("INSERT INTO new_department (id_department, name_department, description) SELECT id_department, name_department, description FROM DepartmentEntity")
                    execSQL("DROP TABLE DepartmentEntity")
                    execSQL("ALTER TABLE new_department RENAME TO DepartmentEntity")
                }
            }
        }
    }

    abstract fun userDao(): UserDao

    abstract fun departmentDao(): DepartmentDao

    abstract fun roomDao(): RoomDao

}