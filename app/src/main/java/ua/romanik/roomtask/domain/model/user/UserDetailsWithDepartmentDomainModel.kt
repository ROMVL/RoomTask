package ua.romanik.roomtask.domain.model.user

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ua.romanik.roomtask.domain.model.department.DepartmentDomainModel

@Parcelize
data class UserDetailsWithDepartmentDomainModel(
    val userDomainModel: UserDomainModel,
    val departmentDomainModel: DepartmentDomainModel
) : Parcelable