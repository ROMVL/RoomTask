package ua.romanik.roomtask.presentation.ui.fragment.department.navigation

import kotlinx.android.parcel.Parcelize
import ua.romanik.roomtask.domain.model.department.DepartmentDomainModel
import ua.romanik.roomtask.presentation.base.navigationevent.BaseNavigation


sealed class DepartmentNavigation : BaseNavigation() {
    @Parcelize object CreateDepartment : DepartmentNavigation()
    @Parcelize data class UpdateDepartment(val departmentDomainModel: DepartmentDomainModel) : DepartmentNavigation()
    @Parcelize object Back : DepartmentNavigation()
    @Parcelize data class Details(val departmentId: Long) : DepartmentNavigation()
}