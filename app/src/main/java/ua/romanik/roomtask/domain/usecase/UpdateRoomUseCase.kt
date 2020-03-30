package ua.romanik.roomtask.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.inject
import ua.romanik.roomtask.domain.mapper.RoomMapper
import ua.romanik.roomtask.domain.model.room.RoomDomainModel
import ua.romanik.roomtask.domain.repository.RoomRepository
import ua.romanik.roomtask.domain.usecase.base.BaseAsyncUseCase

interface UpdateRoomUseCase {
    suspend fun execute(roomDomainModel: RoomDomainModel)
}

class UpdateRoomUseCaseImpl(
    private val roomRepository: RoomRepository
) : BaseAsyncUseCase<Unit>(), UpdateRoomUseCase {

    private val roomMapper by inject<RoomMapper>()
    private lateinit var roomDomainModel: RoomDomainModel

    override suspend fun execute(roomDomainModel: RoomDomainModel) {
        this.roomDomainModel = roomDomainModel
        execute()
    }

    override suspend fun execute() = withContext(Dispatchers.IO) {
        roomRepository.updateRoom(roomMapper.mapDomainModelToEntity(roomDomainModel))
    }

}