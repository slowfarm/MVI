package com.inc.eva.mvi.ui.base

import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.inc.eva.mvi.mvvm.SingleEvent
import com.inc.eva.mvi.mvvm.SingleLiveEvent

abstract class BaseViewModel<ViewState, Event> : ViewModel() {

    private val viewStateLiveData = MutableLiveData<ViewState>()
    private val eventsLiveData = SingleLiveEvent<Event>()

    protected var viewState: ViewState
        get() = viewStateLiveData.value!!
        set(value) {
            viewStateLiveData.value = value!!
        }

    fun getViewStateObservable(): LiveData<ViewState> = viewStateLiveData

    fun getEventObservable(): SingleLiveEvent<Event> = eventsLiveData

    @CallSuper
    open fun initialize() {
        if (viewStateLiveData.value == null) {
            viewState = getInitialViewState()
        }
    }

    protected fun sendEvent(event: Event) {
        eventsLiveData.postValue(SingleEvent(event))
    }

    protected abstract fun getInitialViewState(): ViewState
}
