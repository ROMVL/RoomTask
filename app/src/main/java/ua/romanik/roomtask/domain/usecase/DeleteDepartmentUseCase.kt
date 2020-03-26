package ua.romanik.roomtask.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.inject
import ua.romanik.roomtask.domain.mapper.DepartmentMapper
import ua.romanik.roomtask.domain.model.department.DepartmentDomainModel
import ua.romanik.roomtask.domain.repository.DepartmentRepository
import ua.romanik.roomtask.domain.usecase.base.BaseAsyncUseCase

interface DeleteDepartmentUseCase {
    suspend fun execute(domainModel: DepartmentDomainModel)
}

class DeleteDepartmentUseCaseImpl(
    private val departmentRepository: DepartmentRepository
) : BaseAsyncUseCase<Unit>(), DeleteDepartmentUseCase {

    private val departmentMapper by inject<DepartmentMapper>()
    private lateinit var departmentDomainModel: DepartmentDomainModel

    override suspend fun execute() {
        withContext(Dispatchers.IO) {
            departmentRepository.deleteDepartment(
                departmentMapper.mapDepartmentDomainModelToEntity(departmentDomainModel)
            )
        }
    }

    override suspend fun execute(domainModel: DepartmentDomainModel) {
        departmentDomainModel = domainModel
        execute()
    }

}