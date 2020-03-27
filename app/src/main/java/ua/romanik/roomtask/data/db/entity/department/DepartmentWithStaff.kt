package ua.romanik.roomtask.data.db.entity.department

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.android.parcel.Parcelize
import ua.romanik.roomtask.data.db.entity.user.UserEntity
import ua.romanik.roomtask.data.db.entity.user.UserEntityWithInfo

@Parcelize
data class DepartmentWithStaff(
    @Embedded val departmentEntity: DepartmentEntity,
    @Relation(parentColumn = "id_department", entityColumn = "departmentId", entity = UserEntity::class) val userEntities: List<UserEntityWithInfo>
) : Parcelable