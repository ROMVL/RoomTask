package ua.romanik.roomtask.data.db.entity.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "DepartmentRoom")
@Parcelize
data class RoomEntity(
    @PrimaryKey(autoGenerate = true) val idRoom: Long,
    val roomNumber: Long
) : Parcelable