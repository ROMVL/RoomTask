package ua.romanik.roomtask.domain.model.room

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RoomDomainModel(
    val id: Long = 0L,
    val number: Long
) : Parcelable