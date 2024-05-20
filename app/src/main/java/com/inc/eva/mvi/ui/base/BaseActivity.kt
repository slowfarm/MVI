package com.inc.eva.mvi.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<Binding : ViewBinding, ViewModel : BaseViewModel<ViewState, Event>, ViewState, Event> :
    AppCompatActivity() {

    protected val binding: Binding get() = _binding as Binding

    protected abstract val bindingInflater: (LayoutInflater) -> Binding
    protected abstract val viewModel: ViewModel

    private var _binding: Binding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(binding.root)

        viewModel.initialize()
        viewModel.getViewStateObservable().observe(this) { render(it) }
        viewModel.getEventObservable().observe(this) {
            handleEvent(it.getContentIfNotHandled() ?: return@observe)
        }

        setupUI()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    protected open fun render(viewState: ViewState) {}

    @SuppressWarnings("OptionalUnit")
    protected open fun handleEvent(event: Event) = Unit

    abstract fun setupUI()
}
