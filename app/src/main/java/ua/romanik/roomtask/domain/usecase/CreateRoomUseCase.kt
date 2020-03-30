package ua.romanik.roomtask.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.inject
import ua.romanik.roomtask.domain.mapper.RoomMapper
import ua.romanik.roomtask.domain.model.room.RoomDomainModel
import ua.romanik.roomtask.domain.repository.RoomRepository
import ua.romanik.roomtask.domain.usecase.base.BaseAsyncUseCase

interface CreateRoomUseCase {
    suspend fun execute(roomDomainModel: RoomDomainModel): Long
}

class CreateRoomUseCaseImpl(
    private val roomRepository: RoomRepository
) : BaseAsyncUseCase<Long>(), CreateRoomUseCase {

    private val roomMapper by inject<RoomMapper>()
    private lateinit var roomDomainModel: RoomDomainModel

    override suspend fun execute(roomDomainModel: RoomDomainModel): Long {
        this.roomDomainModel = roomDomainModel
        return execute()
    }

    override suspend fun execute() = withContext(Dispatchers.IO) {
        roomRepository.saveRoom(roomMapper.mapDomainModelToEntity(roomDomainModel))
    }

}