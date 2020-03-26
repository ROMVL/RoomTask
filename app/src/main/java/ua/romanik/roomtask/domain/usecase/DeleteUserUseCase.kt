package ua.romanik.roomtask.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.inject
import ua.romanik.roomtask.domain.mapper.UserMapper
import ua.romanik.roomtask.domain.model.user.UserDomainModel
import ua.romanik.roomtask.domain.repository.UserRepository
import ua.romanik.roomtask.domain.usecase.base.BaseAsyncUseCase

interface DeleteUserUseCase {
    suspend fun execute(userDomainModel: UserDomainModel)
}

class DeleteUserUseCaseImpl(
    private val userRepository: UserRepository
) : BaseAsyncUseCase<Unit>(), DeleteUserUseCase {

    private val userMapper by inject<UserMapper>()
    private lateinit var userDomainModel: UserDomainModel

    override suspend fun execute(userDomainModel: UserDomainModel) {
        this.userDomainModel = userDomainModel
        execute()
    }

    override suspend fun execute() {
        withContext(Dispatchers.IO) {
            userRepository.deleteUser(userMapper.mapUserDomainModelToEntity(userDomainModel))
        }
    }

}