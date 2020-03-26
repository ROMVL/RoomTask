package ua.romanik.roomtask.data.db.entity.department

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(indices = [Index("id")])
@Parcelize
data class DepartmentEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val description: String
) : Parcelable