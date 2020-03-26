package ua.romanik.roomtask.domain.usecase.base

import org.koin.core.KoinComponent

abstract class BaseAsyncUseCase<T> : KoinComponent {

    protected abstract suspend fun execute(): T

}