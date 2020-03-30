package ua.romanik.roomtask.domain.mapper

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ua.romanik.roomtask.data.db.entity.user.UserEntity
import ua.romanik.roomtask.data.db.entity.user.UserEntityWithInfo
import ua.romanik.roomtask.data.db.entity.user.UserInfoEntity
import ua.romanik.roomtask.domain.model.user.UserDomainModel

class UserMapper {

    suspend fun mapListUserWithInfoEntityToDomainModel(userEntityWithInfoList: List<UserEntityWithInfo>): List<UserDomainModel> {
        return withContext(Dispatchers.Default) {
            userEntityWithInfoList.map {
                mapUserWithInfoEntityToDomainModel(it)
            }
        }
    }

    suspend fun mapUserWithInfoEntityToDomainModel(userEntityWithInfo: UserEntityWithInfo): UserDomainModel {
        return withContext(Dispatchers.Default) {
            UserDomainModel(
                id = userEntityWithInfo.userEntity.id,
                departmentId = userEntityWithInfo.userEntity.departmentId,
                email = userEntityWithInfo.userEntity.email,
                name = userEntityWithInfo.userInfoEntity.name,
                phone = userEntityWithInfo.userInfoEntity.phone
            )
        }
    }

    suspend fun mapUserDomainModelToEntity(userDomainModel: UserDomainModel): UserEntityWithInfo {
        return withContext(Dispatchers.Default) {
            UserEntityWithInfo(
                UserEntity(
                    userDomainModel.id,
                    userDomainModel.departmentId,
                    userDomainModel.email
                ),
                UserInfoEntity(
                    userDomainModel.id,
                    userDomainModel.id,
                    userDomainModel.name,
                    userDomainModel.phone
                )
            )
        }
    }

}