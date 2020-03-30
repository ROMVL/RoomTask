package ua.romanik.roomtask.domain.repository

import kotlinx.coroutines.flow.Flow
import ua.romanik.roomtask.data.db.entity.room.RoomEntity

interface RoomRepository {

    fun fetchAllRooms(): Flow<List<RoomEntity>>

    fun fetchRoomById(roomId: Long): Flow<RoomEntity>

    suspend fun saveRoom(roomEntity: RoomEntity): Long

    suspend fun updateRoom(roomEntity: RoomEntity)

    suspend fun deleteRoom(roomEntity: RoomEntity)

}