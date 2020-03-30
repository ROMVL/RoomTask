package ua.romanik.roomtask.data.db.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ua.romanik.roomtask.data.db.entity.room.RoomEntity

@Dao
abstract class RoomDao {

    @Query("SELECT * FROM DepartmentRoom")
    abstract fun fetchAllRooms(): Flow<List<RoomEntity>>

    @Query("SELECT * FROM DepartmentRoom WHERE idRoom = :roomId")
    abstract fun fetchRoomById(roomId: Long): Flow<RoomEntity>

    @Insert
    abstract suspend fun save(roomEntity: RoomEntity): Long

    @Transaction
    @Update
    abstract suspend fun update(roomEntity: RoomEntity)

    @Transaction
    @Delete
    abstract suspend fun delete(roomEntity: RoomEntity)


}