package com.example.core.base

import com.example.core.SchedulerProvider
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class UseCase<T>(
    private val schedulerProvider: SchedulerProvider
) {

    protected abstract fun buildUseCase(queryParameters: Map<String,Any>): Single<T> // todo
    private val compositeDisposable = CompositeDisposable()

    fun execute(
        queryParameters: Map<String,Any> = emptyMap(),
        onStart: () -> Unit = {},
        onSuccess: ((t: T) -> Unit),
        onError: ((t: Throwable) -> Unit),
        onFinished: () -> Unit = {}
    ) {
        onStart()
        compositeDisposable.add(
            buildUseCase(queryParameters)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .doAfterTerminate(onFinished)
                .subscribe(onSuccess, onError)
        )
    }

    fun dispose() {
        compositeDisposable.clear()
    }
}