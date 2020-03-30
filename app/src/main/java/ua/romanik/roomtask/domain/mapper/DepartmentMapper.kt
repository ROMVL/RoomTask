package ua.romanik.roomtask.domain.mapper

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ua.romanik.roomtask.data.db.entity.department.DepartmentEntity
import ua.romanik.roomtask.data.db.entity.department.DepartmentWithStaff
import ua.romanik.roomtask.data.db.entity.department.DepartmentWithStuffAndRoom
import ua.romanik.roomtask.data.db.entity.room.RoomEntity
import ua.romanik.roomtask.domain.model.department.DepartmentDomainModel
import ua.romanik.roomtask.domain.model.department.DepartmentWithStuffAndRoomDomainModel
import ua.romanik.roomtask.domain.model.department.DepartmentWithStuffDomainModel

class DepartmentMapper(
    private val userMapper: UserMapper,
    private val roomMapper: RoomMapper
) {

    fun mapDepartmentEntityToDomainModel(departmentEntity: DepartmentEntity): DepartmentDomainModel {
        return DepartmentDomainModel(
            id = departmentEntity.id,
            name = departmentEntity.name,
            description = departmentEntity.description,
            roomId = departmentEntity.roomId ?: Long.MIN_VALUE
        )
    }

    suspend fun mapDepartmentDomainModelToEntity(departmentDomainModel: DepartmentDomainModel): DepartmentEntity {
        return withContext(Dispatchers.Default) {
            DepartmentEntity(
                id = departmentDomainModel.id,
                name = departmentDomainModel.name,
                description = departmentDomainModel.description,
                roomId = if (departmentDomainModel.roomId == Long.MIN_VALUE) null else departmentDomainModel.roomId
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

    suspend fun mapDepartmentWithStuffAndRoomEntityToDomainModel(
        departmentWithStuffAndRoom: DepartmentWithStuffAndRoom
    ): DepartmentWithStuffAndRoomDomainModel = withContext(Dispatchers.Default) {
        DepartmentWithStuffAndRoomDomainModel(
            id = departmentWithStuffAndRoom.departmentEntity.id,
            name = departmentWithStuffAndRoom.departmentEntity.name,
            description = departmentWithStuffAndRoom.departmentEntity.description,
            roomDomainModel = roomMapper.mapEntityToDomainModel(
                departmentWithStuffAndRoom.roomEntity ?: RoomEntity(Long.MIN_VALUE, Long.MIN_VALUE)
            ),
            users =
            userMapper.mapListUserWithInfoEntityToDomainModel(departmentWithStuffAndRoom.userEntities)
        )
    }

    suspend fun mapDepartmentWithStuffAndRoomDomainModelToDepartmentDomainModel(
        departmentWithStuffDomainModel: DepartmentWithStuffAndRoomDomainModel
    ): DepartmentDomainModel {
        return withContext(Dispatchers.Default) {
            DepartmentDomainModel(
                id = departmentWithStuffDomainModel.id,
                name = departmentWithStuffDomainModel.name,
                description = departmentWithStuffDomainModel.description,
                roomId = departmentWithStuffDomainModel.roomDomainModel.id
            )
        }
    }


}