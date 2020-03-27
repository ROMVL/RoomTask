package ua.romanik.roomtask.presentation.ui.fragment.user.navigation

import kotlinx.android.parcel.Parcelize
import ua.romanik.roomtask.domain.model.user.UserDomainModel
import ua.romanik.roomtask.presentation.base.navigationevent.BaseNavigation

sealed class UserNavigation : BaseNavigation() {
    @Parcelize
    object CreateUser : UserNavigation()
    @Parcelize
    data class UpdateUser(val userDomainModel: UserDomainModel) : UserNavigation()
    @Parcelize
    object Back : UserNavigation()
    @Parcelize
    data class Details(val userId: Long) : UserNavigation()
}