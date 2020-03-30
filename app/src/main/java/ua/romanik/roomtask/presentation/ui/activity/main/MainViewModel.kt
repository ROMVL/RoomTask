package ua.romanik.roomtask.presentation.ui.activity.main

import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ua.romanik.roomtask.data.db.dao.DepartmentDao
import ua.romanik.roomtask.data.db.dao.RoomDao
import ua.romanik.roomtask.data.db.dao.UserDao
import ua.romanik.roomtask.data.db.entity.department.DepartmentEntity
import ua.romanik.roomtask.data.db.entity.room.RoomEntity
import ua.romanik.roomtask.data.db.entity.user.UserEntity
import ua.romanik.roomtask.data.db.entity.user.UserEntityWithInfo
import ua.romanik.roomtask.data.db.entity.user.UserInfoEntity
import ua.romanik.roomtask.presentation.base.viewmodel.BaseViewModel

@ExperimentalCoroutinesApi
class MainViewModel(
    private val userDao: UserDao,
    private val departmentDao: DepartmentDao,
    private val roomDao: RoomDao
) : BaseViewModel() {

    init {
        //fakeData()
    }

    private fun fakeData() {
        viewModelScopeWithErrorHandler.launch {
            createDepartments().forEach { departmentId ->
                val userEntity = UserEntity(0L, departmentId, "vrom@gmail.com")
                val userInfoEntity = UserInfoEntity(0L, 0L, "Vladyslav", 380988577995L)
                val userEntityWithInfo = UserEntityWithInfo(userEntity, userInfoEntity)

                val userEntity2 = UserEntity(0L, departmentId, "romanik@gmail.com")
                val userInfoEntity2 =
                    UserInfoEntity(0L, 0L, "Vladyslav",  380988577995L)
                val userEntityWithInfo2 = UserEntityWithInfo(userEntity2, userInfoEntity2)

                userDao.insert(userEntityWithInfo)
                userDao.insert(userEntityWithInfo2)
            }

            for (i in 0L..15L) {
                roomDao.save(RoomEntity(0, i))
            }
            userDao.fetchUsers()
                .onEach {
                    Log.d(MainViewModel::class.java.simpleName, "userEntityWithInfo fetched -> $it")
                }
                .flowOn(Dispatchers.IO)
                .launchIn(viewModelScope)

            departmentDao.fetchAllDepartments()
                .onEach {
                    Log.d(MainViewModel::class.java.simpleName, "departments -> $it")
                }
                .flowOn(Dispatchers.IO)
                .launchIn(viewModelScope)

            departmentDao.fetchAllDepartmentsWithStuff()
                .onEach {
                    Log.d(MainViewModel::class.java.simpleName, "departments with stuff -> $it")
                }
                .flowOn(Dispatchers.IO)
                .launchIn(viewModelScope)
        }
    }

    private suspend fun createDepartments(): List<Long> {
        val ids = mutableListOf<Long>()
        for (i in 0..15) {
            val departmentEntity = DepartmentEntity(0, "Department Name #$i", "Description #$i")
            val id = departmentDao.insert(departmentEntity)
            ids.add(id)
        }
        return ids
    }
}