package ua.romanik.roomtask.presentation.base.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.plus
import org.koin.core.KoinComponent
import ua.romanik.roomtask.presentation.base.livedata.Event
import ua.romanik.roomtask.presentation.base.livedata.SingleLiveEvent
import ua.romanik.roomtask.presentation.base.navigationevent.BaseNavigation
import ua.romanik.roomtask.presentation.model.ErrorModel

abstract class BaseViewModel : ViewModel(), KoinComponent {

    val BaseViewModel.viewModelScopeWithErrorHandler: CoroutineScope get() = viewModelScope + errorHandler
    protected val errorHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.message?.let { errorMessage ->
            Log.e("BaseViewModel", "error -> $errorMessage")
            _errorLiveEvent.value = Event(ErrorModel(errorMessage))
        }

    }

    protected val _errorLiveEvent = SingleLiveEvent<Event<ErrorModel>>()
    protected val _navigationLiveEvent = SingleLiveEvent<Event<BaseNavigation>>()
    protected val _loadingStateLiveEvent = SingleLiveEvent<Event<Boolean>>()

    val errorLiveEvent: SingleLiveEvent<Event<ErrorModel>> = _errorLiveEvent
    val navigationLiveEvent: SingleLiveEvent<Event<BaseNavigation>> = _navigationLiveEvent
    val loadingStateLiveEvent: SingleLiveEvent<Event<Boolean>> = _loadingStateLiveEvent

}

