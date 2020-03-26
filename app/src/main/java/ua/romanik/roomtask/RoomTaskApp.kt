package ua.romanik.roomtask

import android.app.Application
import android.util.Log
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ua.romanik.roomtask.data.db.DepartmentDataBase
import ua.romanik.roomtask.data.db.dao.UserDao
import ua.romanik.roomtask.data.db.entity.user.UserEntity
import ua.romanik.roomtask.data.db.entity.user.UserEntityWithInfo
import ua.romanik.roomtask.data.db.entity.user.UserInfoEntity
import ua.romanik.roomtask.di.*

@ExperimentalCoroutinesApi
class RoomTaskApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@RoomTaskApp)
            modules(
                listOf(
                    viewModelModule,
                    databaseModule,
                    repositoryModule,
                    domainMapperModule,
                    useCaseModule
                )
            )
        }
    }

}