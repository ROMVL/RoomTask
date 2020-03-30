package ua.romanik.roomtask.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import org.koin.core.inject
import ua.romanik.roomtask.domain.mapper.RoomMapper
import ua.romanik.roomtask.domain.model.room.RoomDomainModel
import ua.romanik.roomtask.domain.repository.RoomRepository
import ua.romanik.roomtask.domain.usecase.base.BaseUseCase

interface FetchRoomsUseCase {
    fun execute(): Flow<List<RoomDomainModel>>
}

@ExperimentalCoroutinesApi
class FetchRoomsUseCaseImpl(
    private val roomRepository: RoomRepository
) : BaseUseCase<Flow<List<RoomDomainModel>>>(), FetchRoomsUseCase {

    private val roomMapper by inject<RoomMapper>()

    override fun execute() = roomRepository.fetchAllRooms()
        .flowOn(Dispatchers.IO)
        .map { entities -> entities.map { roomMapper.mapEntityToDomainModel(it) } }
        .flowOn(Dispatchers.Default)

}