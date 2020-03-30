package ua.romanik.roomtask.domain.mapper

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ua.romanik.roomtask.data.db.entity.room.RoomEntity
import ua.romanik.roomtask.domain.model.room.RoomDomainModel

class RoomMapper {

    fun mapEntityToDomainModel(roomEntity: RoomEntity): RoomDomainModel {
        return RoomDomainModel(
            id = roomEntity.idRoom,
            number = roomEntity.roomNumber
        )
    }

    suspend fun mapDomainModelToEntity(roomDomainModel: RoomDomainModel): RoomEntity = withContext(Dispatchers.Default) {
        RoomEntity(
            idRoom = roomDomainModel.id,
            roomNumber = roomDomainModel.number
        )
    }

}