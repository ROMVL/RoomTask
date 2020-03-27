package ua.romanik.roomtask.domain.usecase

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.koin.core.inject
import ua.romanik.roomtask.domain.mapper.DepartmentMapper
import ua.romanik.roomtask.domain.mapper.UserMapper
import ua.romanik.roomtask.domain.model.user.UserDetailsWithDepartmentDomainModel
import ua.romanik.roomtask.domain.repository.UserRepository
import ua.romanik.roomtask.domain.usecase.base.BaseUseCase

interface FetchUserDetailsByIdUseCase {
    fun execute(userId: Long): Flow<UserDetailsWithDepartmentDomainModel>
}

@ExperimentalCoroutinesApi
class FetchUserDetailsByIdUseCaseImpl(
    private val userRepository: UserRepository
) : BaseUseCase<Flow<UserDetailsWithDepartmentDomainModel>>(), FetchUserDetailsByIdUseCase {

    private val userMapper by inject<UserMapper>()
    private val departmentMapper by inject<DepartmentMapper>()
    private var userId: Long = Long.MIN_VALUE

    override fun execute(userId: Long): Flow<UserDetailsWithDepartmentDomainModel> {
        this.userId = userId
        return execute()
    }

    override fun execute() = userRepository.fetchUserByIdWithDepartment(userId)
        .flowOn(Dispatchers.IO)
        .map {
            Log.d(FetchUserDetailsByIdUseCaseImpl::class.java.simpleName, "UserWithDep -> $it")
            UserDetailsWithDepartmentDomainModel(
                userMapper.mapUserWithInfoEntityToDomainModel(it.userEntity),
                departmentMapper.mapDepartmentEntityToDomainModel(it.departmentEntity)
            )
        }
        .onEach {
            Log.d(FetchUserDetailsByIdUseCaseImpl::class.java.simpleName, "UserWithDepMapped -> $it")
        }
        .flowOn(Dispatchers.Default)

}