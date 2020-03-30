package ua.romanik.roomtask.domain.repository

import kotlinx.coroutines.flow.Flow
import ua.romanik.roomtask.data.db.entity.department.DepartmentEntity
import ua.romanik.roomtask.data.db.entity.department.DepartmentWithStaff
import ua.romanik.roomtask.data.db.entity.department.DepartmentWithStuffAndRoom

interface DepartmentRepository {
    fun fetchDepartments(): Flow<List<DepartmentEntity>>
    fun fetchDepartmentById(id: Long): Flow<DepartmentEntity>
    fun fetchDepartmentsWithStuff(): Flow<List<DepartmentWithStaff>>
    fun fetchDepartmentByIdWithStuff(id: Long): Flow<DepartmentWithStaff>
    fun fetchDepartmentByIdWithStuffAndRoom(id: Long): Flow<DepartmentWithStuffAndRoom>
    suspend fun saveDepartment(departmentEntity: DepartmentEntity): Long
    suspend fun deleteDepartment(departmentEntity: DepartmentEntity)
    suspend fun updateDepartment(departmentEntity: DepartmentEntity)
}