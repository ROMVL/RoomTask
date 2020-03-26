package ua.romanik.roomtask.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.inject
import ua.romanik.roomtask.domain.mapper.DepartmentMapper
import ua.romanik.roomtask.domain.model.department.DepartmentDomainModel
import ua.romanik.roomtask.domain.repository.DepartmentRepository
import ua.romanik.roomtask.domain.usecase.base.BaseAsyncUseCase

interface CreateDepartmentUseCase {
    suspend fun execute(departmentDomainModel: DepartmentDomainModel)
}

class CreateDepartmentUseCaseImpl(
    private val departmentRepository: DepartmentRepository
) : BaseAsyncUseCase<Long>(), CreateDepartmentUseCase {

    private val departmentMapper: DepartmentMapper by inject()
    private lateinit var departmentDomainModel: DepartmentDomainModel

    override suspend fun execute(departmentDomainModel: DepartmentDomainModel) {
        this.departmentDomainModel = departmentDomainModel
        execute()
    }

    override suspend fun execute(): Long = withContext(Dispatchers.IO) {
        departmentRepository
            .saveDepartment(departmentMapper.mapDepartmentDomainModelToEntity(departmentDomainModel))
    }

}