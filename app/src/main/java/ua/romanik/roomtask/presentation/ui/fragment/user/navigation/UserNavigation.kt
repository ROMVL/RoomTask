package ua.romanik.roomtask.presentation.ui.fragment.user.navigation

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ua.romanik.roomtask.domain.model.user.UserDomainModel
import ua.romanik.roomtask.presentation.base.navigationevent.BaseNavigation

sealed class UserNavigation : BaseNavigation() {
    @Parcelize
    object CreateDepartment : UserNavigation(), Parcelable
    @Parcelize
    data class UpdateDepartment(val userDomainModel: UserDomainModel) : UserNavigation(), Parcelable
}