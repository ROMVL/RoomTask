package ua.romanik.roomtask.presentation.base.fragment

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import ua.romanik.roomtask.presentation.base.hideKeyboard
import ua.romanik.roomtask.presentation.base.navigationevent.BaseNavigation
import ua.romanik.roomtask.presentation.base.viewmodel.BaseViewModel

abstract class BaseFragment<T : BaseViewModel>(@LayoutRes val layout: Int) : Fragment(layout) {

    abstract val viewModel: T

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolBar()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(viewModel) {
            errorLiveEvent.observe(
                this@BaseFragment.viewLifecycleOwner,
                Observer { showError(it.content.message) }
            )
            navigationLiveEvent.observe(
                this@BaseFragment.viewLifecycleOwner,
                Observer { handleNavigationEvent(it.content) }
            )
            loadingStateLiveEvent.observe(
                this@BaseFragment.viewLifecycleOwner,
                Observer { handleLoadingStateEvent(it.content) }
            )
        }
    }

    override fun onPause() {
        hideKeyboard()
        super.onPause()
    }

    protected fun showError(message: String) {
        view?.let { Snackbar.make(it, message, Snackbar.LENGTH_LONG).show() }
    }

    open fun <T : BaseNavigation> handleNavigationEvent(navigationEvent: T) = Unit

    open fun handleLoadingStateEvent(state: Boolean) = Unit

    open fun initToolBar() = Unit

}