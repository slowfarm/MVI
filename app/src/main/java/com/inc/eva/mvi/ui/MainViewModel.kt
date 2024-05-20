package com.inc.eva.mvi.ui

import androidx.lifecycle.viewModelScope
import com.inc.eva.mvi.ui.MainViewModel.Event
import com.inc.eva.mvi.ui.MainViewModel.Event.Message
import com.inc.eva.mvi.ui.MainViewModel.ViewState
import com.inc.eva.mvi.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : BaseViewModel<ViewState, Event>() {

    override fun getInitialViewState(): ViewState = ViewState(isLoading = true)

    fun getText() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(2000)
            withContext(Dispatchers.Main) {
                viewState = viewState.copy(isLoading = false, text = "Hello world!")
                sendEvent(Message("Complete"))
            }
        }
    }

    data class ViewState(
        val isLoading: Boolean,
        val text: String? = null,
    )

    sealed class Event {
        data class Message(val text: String) : Event()
    }
}
