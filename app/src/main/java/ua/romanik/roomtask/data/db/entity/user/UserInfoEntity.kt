package ua.romanik.roomtask.data.db.entity.user

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class UserInfoEntity(
    @PrimaryKey(autoGenerate = true) val userInfoId: Long,
    val userId: Long,
    @ColumnInfo(name = "name_user")val name: String,
    val phone: String
) : Parcelable