package ua.romanik.roomtask.domain.usecase.base

import org.koin.core.KoinComponent

abstract class BaseUseCase<T> : KoinComponent {

    protected abstract fun execute(): T

}