package ua.romanik.roomtask.data.db.entity.user

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserEntityWithInfo(
    @Embedded val userEntity: UserEntity,
    @Relation(parentColumn = "id", entityColumn = "userId") val userInfoEntity: UserInfoEntity
) : Parcelable