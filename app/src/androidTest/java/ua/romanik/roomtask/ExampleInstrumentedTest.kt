package ua.romanik.roomtask

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.java.KoinJavaComponent.get
import org.koin.java.KoinJavaComponent.getKoin
import ua.romanik.roomtask.data.db.dao.UserDao
import ua.romanik.roomtask.data.db.entity.user.UserEntity
import ua.romanik.roomtask.data.db.entity.user.UserEntityWithInfo
import ua.romanik.roomtask.data.db.entity.user.UserInfoEntity

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("ua.romanik.roomtask", appContext.packageName)
    }

    @Test
    fun testInsertUserAndFetch() = runBlocking {
        val userEntity = UserEntity(0L, "vlad.romanik@gmail.com")
        val userInfoEntity = UserInfoEntity(0L, 0L, "Vlad", "Kharkiv", "+380988577995")
        val userEntityWithInfo = UserEntityWithInfo(userEntity, listOf(userInfoEntity))
        val userDao = getKoin().get<UserDao>()
        userDao.insert(userEntityWithInfo)
        userDao.fetchUsers()
            .onEach { println("users -> $it") }
            .launchIn(this)
    }
}
