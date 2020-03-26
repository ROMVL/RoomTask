package ua.romanik.roomtask.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ErrorModel(
    val message: String
) : Parcelable