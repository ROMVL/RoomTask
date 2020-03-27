package ua.romanik.roomtask.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import org.koin.core.inject
import ua.romanik.roomtask.domain.mapper.DepartmentMapper
import ua.romanik.roomtask.domain.model.department.DepartmentWithStuffDomainModel
import ua.romanik.roomtask.domain.repository.DepartmentRepository
import ua.romanik.roomtask.domain.usecase.base.BaseUseCase

interface FetchDepartmentByIdWithStuff {
    fun execute(departmentId: Long): Flow<DepartmentWithStuffDomainModel>
}

@ExperimentalCoroutinesApi
class FetchDepartmentByIdWithStuffImpl(
    private val departmentRepository: DepartmentRepository
) : BaseUseCase<Flow<DepartmentWithStuffDomainModel>>(), FetchDepartmentByIdWithStuff {

    private val departmentMapper by inject<DepartmentMapper>()
    private var departmentId: Long = Long.MIN_VALUE

    override fun execute(departmentId: Long): Flow<DepartmentWithStuffDomainModel> {
        this.departmentId = departmentId
        return execute()
    }

    override fun execute() = departmentRepository.fetchDepartmentByIdWithStuff(departmentId)
        .flowOn(Dispatchers.IO)
        .map { departmentMapper.mapDepartmentWithStuffEntityToDomainModel(it) }
        .flowOn(Dispatchers.Default)

}