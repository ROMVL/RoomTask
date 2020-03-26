package ua.romanik.roomtask.domain.model.department

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DepartmentDomainModel(
    val id: Long = 0L,
    val name: String,
    val description: String
) : Parcelable