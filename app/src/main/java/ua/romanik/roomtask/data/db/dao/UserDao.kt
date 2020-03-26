package ua.romanik.roomtask.data.db.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ua.romanik.roomtask.data.db.entity.user.UserEntity
import ua.romanik.roomtask.data.db.entity.user.UserEntityWithInfo
import ua.romanik.roomtask.data.db.entity.user.UserInfoEntity

@Dao
abstract class UserDao {

    @Transaction
    @Insert
    suspend fun insert(userEntityWithInfo: UserEntityWithInfo) {
        val userId = insert(userEntityWithInfo.userEntity)
        insert(userEntityWithInfo.userInfoEntity.copy(userId = userId))
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(userEntity: UserEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(userInfoEntity: UserInfoEntity): Long

    @Transaction
    @Query("SELECT * FROM UserEntity")
    abstract fun fetchUsers(): Flow<List<UserEntityWithInfo>>

    @Transaction
    @Query("SELECT * FROM UserEntity WHERE id = :userId")
    abstract fun fetchUserById(userId: Long): Flow<UserEntityWithInfo>

    @Transaction
    @Update
    abstract suspend fun update(userEntity: UserEntity)

    @Transaction
    @Update
    abstract suspend fun update(userInfoEntity: UserInfoEntity)

    @Transaction
    @Update
    suspend fun update(userEntityWithInfo: UserEntityWithInfo) {
        update(userEntityWithInfo.userEntity)
        update(userEntityWithInfo.userInfoEntity)
    }

    @Transaction
    @Delete
    abstract suspend fun delete(userEntity: UserEntity)

    @Transaction
    @Delete
    abstract suspend fun delete(userInfoEntity: UserInfoEntity)

    @Transaction
    @Delete
    suspend fun delete(userEntityWithInfo: UserEntityWithInfo) {
        delete(userEntityWithInfo.userInfoEntity)
        delete(userEntityWithInfo.userEntity)
    }

}