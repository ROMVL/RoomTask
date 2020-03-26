package ua.romanik.roomtask.data.db.entity.user

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class UserInfoEntity(
    @PrimaryKey(autoGenerate = true) val userInfoId: Long,
    val userId: Long,
    val name: String,
    val address: String,
    val phone: String
) : Parcelable