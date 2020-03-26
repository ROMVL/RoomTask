package ua.romanik.roomtask.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.inject
import ua.romanik.roomtask.domain.mapper.DepartmentMapper
import ua.romanik.roomtask.domain.model.department.DepartmentDomainModel
import ua.romanik.roomtask.domain.repository.DepartmentRepository
import ua.romanik.roomtask.domain.usecase.base.BaseAsyncUseCase

interface UpdateDepartmentUseCase {
    suspend fun execute(departmentDomainModel: DepartmentDomainModel)
}

class UpdateDepartmentUseCaseImpl(
    private val departmentRepository: DepartmentRepository
) : BaseAsyncUseCase<Unit>(), UpdateDepartmentUseCase {

    private val departmentMapper by inject<DepartmentMapper>()
    private lateinit var departmentDomainModel: DepartmentDomainModel

    override suspend fun execute(departmentDomainModel: DepartmentDomainModel) {
        this.departmentDomainModel = departmentDomainModel
        execute()
    }

    override suspend fun execute() = withContext(Dispatchers.IO) {
        departmentRepository
            .updateDepartment(
                departmentMapper.mapDepartmentDomainModelToEntity(departmentDomainModel)
            )
    }

}