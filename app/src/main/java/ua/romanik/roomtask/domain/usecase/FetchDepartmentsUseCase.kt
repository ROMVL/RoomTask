package ua.romanik.roomtask.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import org.koin.core.inject
import ua.romanik.roomtask.domain.mapper.DepartmentMapper
import ua.romanik.roomtask.domain.model.department.DepartmentDomainModel
import ua.romanik.roomtask.domain.repository.DepartmentRepository
import ua.romanik.roomtask.domain.usecase.base.BaseUseCase

interface FetchDepartmentsUseCase {
    fun execute(): Flow<List<DepartmentDomainModel>>
}

@ExperimentalCoroutinesApi
class FetchDepartmentsUseCaseImpl(
    private val departmentsRepository: DepartmentRepository
) : BaseUseCase<Flow<List<DepartmentDomainModel>>>(), FetchDepartmentsUseCase {

    private val departmentMapper by inject<DepartmentMapper>()

    override fun execute(): Flow<List<DepartmentDomainModel>> =
        departmentsRepository.fetchDepartments()
            .distinctUntilChanged()
            .flowOn(Dispatchers.IO)
            .map { departmentsEntity ->
                departmentsEntity.map { departmentEntity ->
                    departmentMapper.mapDepartmentEntityToDomainModel(departmentEntity)
                }
            }
            .flowOn(Dispatchers.Default)

}