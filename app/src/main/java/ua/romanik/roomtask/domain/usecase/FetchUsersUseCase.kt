package ua.romanik.roomtask.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import org.koin.core.inject
import ua.romanik.roomtask.domain.mapper.UserMapper
import ua.romanik.roomtask.domain.model.user.UserDomainModel
import ua.romanik.roomtask.domain.repository.UserRepository
import ua.romanik.roomtask.domain.usecase.base.BaseUseCase

interface FetchUsersUseCase {
    fun execute(): Flow<List<UserDomainModel>>
}

@ExperimentalCoroutinesApi
class FetchUsersUseCaseImpl(
    private val userRepository: UserRepository
) : BaseUseCase<Flow<List<UserDomainModel>>>(), FetchUsersUseCase {

    private val userMapper by inject<UserMapper>()

    override fun execute(): Flow<List<UserDomainModel>> = userRepository.fetchAllUsers()
        .distinctUntilChanged()
        .flowOn(Dispatchers.IO)
        .map { userMapper.mapListUserWithInfoEntityToDomainModel(it) }
        .flowOn(Dispatchers.Default)

}