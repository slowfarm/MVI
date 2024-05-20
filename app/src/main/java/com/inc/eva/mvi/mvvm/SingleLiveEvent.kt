package com.inc.eva.mvi.mvvm

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class SingleLiveEvent<T> : MutableLiveData<SingleEvent<T>>() {
    private val pending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in SingleEvent<T>>) {
        if (hasActiveObservers()) {
            Log.d(
                "SingleLiveEvent",
                "Multiple observers registered but only one will be notified of changes."
            )
        }

        super.observe(owner) { t ->
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        }
    }

    @MainThread
    override fun setValue(t: SingleEvent<T>?) {
        pending.set(true)
        super.setValue(t)
    }
}
