package ua.romanik.roomtask.data.db.entity.user

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.android.parcel.Parcelize
import ua.romanik.roomtask.data.db.entity.department.DepartmentEntity

@Parcelize
data class UserWithDepartment(
    @Embedded val userEntity: UserEntityWithInfo,
    @Embedded val departmentEntity: DepartmentEntity
) : Parcelable