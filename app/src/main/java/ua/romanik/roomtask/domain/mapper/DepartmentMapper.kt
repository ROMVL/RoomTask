package ua.romanik.roomtask.domain.mapper

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ua.romanik.roomtask.data.db.entity.department.DepartmentEntity
import ua.romanik.roomtask.data.db.entity.department.DepartmentWithStaff
import ua.romanik.roomtask.domain.model.department.DepartmentDomainModel
import ua.romanik.roomtask.domain.model.department.DepartmentWithStuffDomainModel

class DepartmentMapper(
    private val userMapper: UserMapper
) {

    fun mapDepartmentEntityToDomainModel(departmentEntity: DepartmentEntity): DepartmentDomainModel {
        return DepartmentDomainModel(
                id = departmentEntity.id,
                name = departmentEntity.name,
                description = departmentEntity.description
            )
    }

    suspend fun mapDepartmentDomainModelToEntity(departmentDomainModel: DepartmentDomainModel): DepartmentEntity {
        return withContext(Dispatchers.Default) {
            DepartmentEntity(
                id = departmentDomainModel.id,
                name = departmentDomainModel.name,
                description = departmentDomainModel.description
            )
        }
    }

    suspend fun mapDepartmentWithStuffEntityToDomainModel(departmentWithStaff: DepartmentWithStaff): DepartmentWithStuffDomainModel {
        return withContext(Dispatchers.Default) {
            DepartmentWithStuffDomainModel(
                id = departmentWithStaff.departmentEntity.id,
                name = departmentWithStaff.departmentEntity.name,
                description = departmentWithStaff.departmentEntity.description,
                users = userMapper.mapListUserWithInfoEntityToDomainModel(departmentWithStaff.userEntities)
            )
        }
    }

    suspend fun mapDepartmentWithStuffDomainModelToDepartmentDomainModel(
        departmentWithStuffDomainModel: DepartmentWithStuffDomainModel
    ): DepartmentDomainModel {
        return withContext(Dispatchers.Default) {
            DepartmentDomainModel(
                id = departmentWithStuffDomainModel.id,
                name = departmentWithStuffDomainModel.name,
                description = departmentWithStuffDomainModel.description
            )
        }
    }

}