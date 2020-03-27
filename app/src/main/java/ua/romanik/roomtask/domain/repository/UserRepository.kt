package ua.romanik.roomtask.domain.repository

import kotlinx.coroutines.flow.Flow
import ua.romanik.roomtask.data.db.entity.user.UserEntityWithInfo
import ua.romanik.roomtask.data.db.entity.user.UserWithDepartment

interface UserRepository {
    fun fetchAllUsers(): Flow<List<UserEntityWithInfo>>
    fun fetchUserById(userId: Long): Flow<UserEntityWithInfo>
    fun fetchUserByIdWithDepartment(userId: Long): Flow<UserWithDepartment>
    suspend fun saveUser(userEntityWithInfo: UserEntityWithInfo)
    suspend fun deleteUser(userEntityWithInfo: UserEntityWithInfo)
    suspend fun updateUser(userEntityWithInfo: UserEntityWithInfo)
}