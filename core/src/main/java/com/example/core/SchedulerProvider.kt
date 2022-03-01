package com.example.core

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

interface SchedulerProvider {
    fun computation(): Scheduler
    fun trampoline(): Scheduler
    fun mainThread(): Scheduler
    fun io(): Scheduler

    class Base @Inject constructor() : SchedulerProvider {
        override fun computation(): Scheduler {
            return Schedulers.computation()
        }

        override fun trampoline(): Scheduler {
            return Schedulers.trampoline()
        }

        override fun mainThread(): Scheduler {
            return AndroidSchedulers.mainThread()
        }

        override fun io(): Scheduler {
            return Schedulers.io()
        }
    }
}