package com.example.core.base

import com.example.core.SchedulerProvider
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class UseCase<T, Data>(
    private val schedulerProvider: SchedulerProvider
) {

    protected abstract fun buildUseCase(data: Data): Single<T> // todo
    private val compositeDisposable = CompositeDisposable()

    fun execute(
        data: Data,
        onStart: () -> Unit = {},
        onSuccess: ((t: T) -> Unit),
        onError: ((t: Throwable) -> Unit),
        onFinished: () -> Unit = {}
    ) {
        onStart()
        compositeDisposable.add(
            buildUseCase(data)
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

//interface UseCaseTest<T> {
//    fun buildUseCase(): Observable<T>
//    val compositeDisposable: CompositeDisposable
//    val schedulerProvider: SchedulerProvider
//
//    fun execute(
//        onStart: () -> Unit = {},
//        onSuccess: ((t: T) -> Unit),
//        onError: ((t: Throwable) -> Unit),
//        onFinished: () -> Unit = {}
//    ) {
//        onStart()
//        compositeDisposable.add(
//            buildUseCase()
//                .subscribeOn(schedulerProvider.io())
//                .observeOn(schedulerProvider.mainThread())
//                .doAfterTerminate(onFinished)
//                .subscribe(onSuccess, onError)
//        )
//    }
//
//    fun dispose() {
//        compositeDisposable.clear()
//    }
//}