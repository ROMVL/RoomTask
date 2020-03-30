package ua.romanik.roomtask.data.db.entity.department

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize
import ua.romanik.roomtask.data.db.entity.room.RoomEntity

@Entity(
    foreignKeys = [ForeignKey(
        entity = RoomEntity::class,
        parentColumns = ["idRoom"],
        childColumns = ["roomId"],
        onDelete = ForeignKey.SET_NULL
    )])
@Parcelize
data class DepartmentEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id_department") val id: Long,
    @ColumnInfo(name = "name_department") val name: String,
    val description: String,
    val roomId: Long? = null
) : Parcelable