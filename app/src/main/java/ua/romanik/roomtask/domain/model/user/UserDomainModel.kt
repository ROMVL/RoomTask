package ua.romanik.roomtask.domain.model.user

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserDomainModel(
    val id: Long = 0L,
    val departmentId: Long,
    val email: String,
    val name: String,
    val address: String,
    val phone: String
) : Parcelable