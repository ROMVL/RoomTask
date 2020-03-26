package ua.romanik.roomtask.data.repository

import ua.romanik.roomtask.data.db.dao.UserDao
import ua.romanik.roomtask.data.db.entity.user.UserEntityWithInfo
import ua.romanik.roomtask.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {

    override fun fetchAllUsers() = userDao.fetchUsers()

    override fun fetchUserById(userId: Long) = userDao.fetchUserById(userId)

    override suspend fun saveUser(userEntityWithInfo: UserEntityWithInfo) = userDao.insert(userEntityWithInfo)

    override suspend fun deleteUser(userEntityWithInfo: UserEntityWithInfo) = userDao.delete(userEntityWithInfo)

    override suspend fun updateUser(userEntityWithInfo: UserEntityWithInfo) = userDao.update(userEntityWithInfo)

}