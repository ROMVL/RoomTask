package ua.romanik.roomtask.domain.usecase

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.inject
import ua.romanik.roomtask.domain.mapper.UserMapper
import ua.romanik.roomtask.domain.model.user.UserDomainModel
import ua.romanik.roomtask.domain.repository.UserRepository
import ua.romanik.roomtask.domain.usecase.base.BaseAsyncUseCase

interface UpdateUserUseCase {
    suspend fun execute(userDomainModel: UserDomainModel)
}

class UpdateUserUseCaseImpl(
    private val userRepository: UserRepository
) : BaseAsyncUseCase<Unit>(), UpdateUserUseCase {

    private val userMapper by inject<UserMapper>()
    private lateinit var userDomainModel: UserDomainModel

    override suspend fun execute(userDomainModel: UserDomainModel) {
        this.userDomainModel = userDomainModel
        execute()
    }

    override suspend fun execute() = withContext(Dispatchers.IO) {
        val mapped = userMapper.mapUserDomainModelToEntity(userDomainModel)
        Log.d(UpdateUserUseCaseImpl::class.java.simpleName, "mapped -> $mapped")
        Log.d(UpdateUserUseCaseImpl::class.java.simpleName, "before -> $userDomainModel")
        userRepository.updateUser(mapped)
    }

}