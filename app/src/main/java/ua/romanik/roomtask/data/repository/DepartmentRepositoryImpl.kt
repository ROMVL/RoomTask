package ua.romanik.roomtask.data.repository

import ua.romanik.roomtask.data.db.dao.DepartmentDao
import ua.romanik.roomtask.data.db.entity.department.DepartmentEntity
import ua.romanik.roomtask.domain.repository.DepartmentRepository

class DepartmentRepositoryImpl(
    private val departmentDao: DepartmentDao
) : DepartmentRepository {

    override fun fetchDepartments() = departmentDao.fetchAllDepartments()

    override fun fetchDepartmentById(id: Long) = departmentDao.fetchDepartmentById(id)

    override fun fetchDepartmentsWithStuff() = departmentDao.fetchAllDepartmentsWithStuff()

    override fun fetchDepartmentByIdWithStuff(id: Long) = departmentDao.fetchDepartmentByIdWithStuff(id)

    override suspend fun saveDepartment(departmentEntity: DepartmentEntity) = departmentDao.insert(departmentEntity)

    override suspend fun deleteDepartment(departmentEntity: DepartmentEntity) = departmentDao.delete(departmentEntity)

    override suspend fun updateDepartment(departmentEntity: DepartmentEntity) = departmentDao.update(departmentEntity)

}