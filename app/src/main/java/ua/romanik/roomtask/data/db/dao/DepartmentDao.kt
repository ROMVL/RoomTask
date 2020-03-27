package ua.romanik.roomtask.data.db.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ua.romanik.roomtask.data.db.entity.department.DepartmentEntity
import ua.romanik.roomtask.data.db.entity.department.DepartmentWithStaff

@Dao
abstract class DepartmentDao {

    @Transaction
    @Query("SELECT * FROM DepartmentEntity")
    abstract fun fetchAllDepartmentsWithStuff(): Flow<List<DepartmentWithStaff>>

    @Query("SELECT * FROM DepartmentEntity")
    abstract fun fetchAllDepartments(): Flow<List<DepartmentEntity>>

    @Query("SELECT * FROM DepartmentEntity WHERE id_department = :departmentId")
    abstract fun fetchDepartmentById(departmentId: Long): Flow<DepartmentEntity>

    @Transaction
    @Query("SELECT * FROM DepartmentEntity WHERE id_department = :departmentId")
    abstract fun fetchDepartmentByIdWithStuff(departmentId: Long): Flow<DepartmentWithStaff>

    @Insert
    abstract suspend fun insert(departmentEntity: DepartmentEntity): Long

    @Transaction
    @Delete
    abstract suspend fun delete(departmentEntity: DepartmentEntity)

    @Transaction
    @Update
    abstract suspend fun update(departmentEntity: DepartmentEntity)

}