package com.inc.eva.mvi.ui

import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.inc.eva.mvi.databinding.ActivityMainBinding
import com.inc.eva.mvi.ui.MainViewModel.Event
import com.inc.eva.mvi.ui.MainViewModel.Event.Message
import com.inc.eva.mvi.ui.MainViewModel.ViewState
import com.inc.eva.mvi.ui.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel, ViewState, Event>() {

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding =
        ActivityMainBinding::inflate
    override val viewModel: MainViewModel by viewModels<MainViewModel>()

    override fun setupUI() {
        viewModel.getText()
    }

    override fun render(viewState: ViewState) {
        with(viewState) {
            binding.progressBar.isVisible = isLoading
            text?.let { binding.textView.text = it }
        }
    }

    override fun handleEvent(event: Event) {
        super.handleEvent(event)

        when (event) {
            is Message -> Toast.makeText(this, event.text, Toast.LENGTH_SHORT).show()
        }
    }
}
