package ua.romanik.roomtask.data.db.entity.user

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize
import ua.romanik.roomtask.data.db.entity.department.DepartmentEntity


@Entity(
    foreignKeys = [ForeignKey(
        entity = DepartmentEntity::class,
        parentColumns = ["id_department"],
        childColumns = ["departmentId"],
        onDelete = ForeignKey.CASCADE
    )]
)
@Parcelize
data class UserEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id_user") val id: Long,
    val departmentId: Long,
    val email: String
) : Parcelable