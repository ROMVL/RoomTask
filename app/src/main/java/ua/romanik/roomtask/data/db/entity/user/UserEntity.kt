package ua.romanik.roomtask.data.db.entity.user

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import ua.romanik.roomtask.data.db.entity.department.DepartmentEntity


@Entity(
    foreignKeys = [ForeignKey(
        entity = DepartmentEntity::class,
        parentColumns = ["id"],
        childColumns = ["departmentId"],
        onDelete = ForeignKey.CASCADE
    )]
)
@Parcelize
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val departmentId: Long,
    val email: String
) : Parcelable