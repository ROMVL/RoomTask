package ua.romanik.roomtask.presentation.ui.fragment.room.navigation

import kotlinx.android.parcel.Parcelize
import ua.romanik.roomtask.domain.model.room.RoomDomainModel
import ua.romanik.roomtask.presentation.base.navigationevent.BaseNavigation

sealed class RoomNavigation : BaseNavigation() {
    @Parcelize
    object CreateRoom : RoomNavigation()
    @Parcelize
    data class UpdateRoom(val roomDomainModel: RoomDomainModel) : RoomNavigation()
    @Parcelize
    object Back : RoomNavigation()
}