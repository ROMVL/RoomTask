package ua.romanik.roomtask.domain.model.department

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ua.romanik.roomtask.domain.model.room.RoomDomainModel
import ua.romanik.roomtask.domain.model.user.UserDomainModel

@Parcelize
data class DepartmentWithStuffAndRoomDomainModel(
    val id: Long,
    val name: String,
    val description: String,
    val roomDomainModel: RoomDomainModel,
    val users: List<UserDomainModel>
) : Parcelable