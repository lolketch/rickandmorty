package com.example.core.base

import com.example.core.SchedulerProvider
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class UseCase<T> (
    private val schedulerProvider: SchedulerProvider
) {

    protected abstract fun buildUseCaseFlowable(): Flowable<T>
    private val compositeDisposable = CompositeDisposable()

    fun execute(
        onStart: () -> Unit = {},
        onSuccess: ((t: T) -> Unit),
        onError: ((t: Throwable) -> Unit),
        onFinished: () -> Unit = {}
    ) {
        onStart()
        compositeDisposable.add(
            buildUseCaseFlowable()
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