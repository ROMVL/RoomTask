package ua.romanik.roomtask.data.db.entity.department

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(indices = [Index("id_department")])
@Parcelize
data class DepartmentEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id_department") val id: Long,
    @ColumnInfo(name = "name_department") val name: String,
    val description: String
) : Parcelable