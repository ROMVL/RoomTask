package ua.romanik.roomtask.presentation.base.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.plus
import org.koin.core.KoinComponent
import ua.romanik.roomtask.presentation.base.livedata.Event
import ua.romanik.roomtask.presentation.base.navigationevent.BaseNavigation
import ua.romanik.roomtask.presentation.model.ErrorModel

abstract class BaseViewModel : ViewModel(), KoinComponent {

    val viewModelScopeWithErrorHandler: CoroutineScope get() = viewModelScope + errorHandler
    protected val errorHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.message?.let { errorMessage ->
            Log.e("BaseViewModel", "error -> $errorMessage")
            _errorLiveEvent.value = Event(ErrorModel(errorMessage))
        }

    }

    protected val _errorLiveEvent = MutableLiveData<Event<ErrorModel>>()
    protected val _navigationLiveEvent = MutableLiveData<Event<BaseNavigation>>()
    protected val _loadingStateLiveEvent = MutableLiveData<Event<Boolean>>()

    val errorLiveEvent: LiveData<Event<ErrorModel>> = _errorLiveEvent
    val navigationLiveEvent: LiveData<Event<BaseNavigation>> = _navigationLiveEvent
    val loadingStateLiveEvent: LiveData<Event<Boolean>> = _loadingStateLiveEvent

}

