package ua.romanik.roomtask.data.repository

import kotlinx.coroutines.flow.Flow
import ua.romanik.roomtask.data.db.dao.RoomDao
import ua.romanik.roomtask.data.db.entity.room.RoomEntity
import ua.romanik.roomtask.domain.repository.RoomRepository

class RoomRepositoryImpl(
    private val roomDao: RoomDao
) : RoomRepository {
    override fun fetchAllRooms() = roomDao.fetchAllRooms()

    override fun fetchRoomById(roomId: Long) = roomDao.fetchRoomById(roomId)

    override suspend fun saveRoom(roomEntity: RoomEntity) = roomDao.save(roomEntity)

    override suspend fun updateRoom(roomEntity: RoomEntity) = roomDao.update(roomEntity)

    override suspend fun deleteRoom(roomEntity: RoomEntity) = roomDao.delete(roomEntity)
}