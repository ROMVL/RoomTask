package ua.romanik.roomtask.presentation.ui.fragment.user.details

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.PopupMenu
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_user_details.*
import kotlinx.android.synthetic.main.toolbar.*
import org.koin.android.ext.android.getKoin
import org.koin.android.viewmodel.ext.android.viewModel
import ua.romanik.roomtask.R
import ua.romanik.roomtask.di.ViewModelPropertyKey
import ua.romanik.roomtask.presentation.base.fragment.BaseFragment
import ua.romanik.roomtask.presentation.base.initText
import ua.romanik.roomtask.presentation.base.navigationevent.BaseNavigation
import ua.romanik.roomtask.presentation.base.visible
import ua.romanik.roomtask.presentation.ui.fragment.user.navigation.UserNavigation


class UserDetailsFragment : BaseFragment<UserDetailsViewModel>(R.layout.fragment_user_details) {

    override val viewModel by viewModel<UserDetailsViewModel>()
    private val args by navArgs<UserDetailsFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getKoin().setProperty(ViewModelPropertyKey.USER_ID, args.userId)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(viewModel) {
            userDetails.observe(
                this@UserDetailsFragment.viewLifecycleOwner,
                Observer {
                    tvUserName.initText(it.userDomainModel.name)
                    tvEmail.initText(it.userDomainModel.email)
                    tvAddress.initText(it.userDomainModel.address)
                    tvPhone.initText(it.userDomainModel.phone)
                    tvDepartmentName.initText(it.departmentDomainModel.name)
                    tvDescription.initText(it.departmentDomainModel.description)
                }
            )
        }
    }

    override fun <T : BaseNavigation> handleNavigationEvent(navigationEvent: T) {
        when (navigationEvent) {
            UserNavigation.Back -> findNavController().popBackStack()
            is UserNavigation.UpdateUser -> findNavController().navigate(
                UserDetailsFragmentDirections.actionUserDetailsFragmentToCreateUserFragment(navigationEvent)
            )
        }
    }

    override fun initToolBar() {
        tvTitle.initText(R.string.details_user)
        with(btnBackArrow) {
            visible()
            setOnClickListener { findNavController().popBackStack() }
        }
        with(btnMenu) {
            visible()
            setOnClickListener { showPopupMenu(it) }
        }
    }

    private fun showPopupMenu(anchor: View) {
        PopupMenu(anchor.context, anchor).apply {
            gravity = Gravity.END
            menuInflater.inflate(R.menu.menu_item, menu)
            setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.itemUpdate -> {
                        viewModel.onClickUpdate()
                        true
                    }
                    R.id.itemDelete -> {
                        viewModel.onClickDelete()
                        true
                    }
                    else -> false
                }
            }
        }.show()
    }
}
