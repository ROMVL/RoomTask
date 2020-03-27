package ua.romanik.roomtask.presentation.ui.fragment.department.details

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_department_details.*
import kotlinx.android.synthetic.main.toolbar.*
import org.koin.android.ext.android.getKoin
import org.koin.android.viewmodel.ext.android.viewModel

import ua.romanik.roomtask.R
import ua.romanik.roomtask.di.ViewModelPropertyKey
import ua.romanik.roomtask.domain.model.user.UserDomainModel
import ua.romanik.roomtask.presentation.base.fragment.BaseFragment
import ua.romanik.roomtask.presentation.base.initText
import ua.romanik.roomtask.presentation.base.navigationevent.BaseNavigation
import ua.romanik.roomtask.presentation.base.visible
import ua.romanik.roomtask.presentation.ui.adapter.user.UserAdapter
import ua.romanik.roomtask.presentation.ui.fragment.department.navigation.DepartmentNavigation
import ua.romanik.roomtask.presentation.ui.fragment.user.navigation.UserNavigation

/**
 * A simple [Fragment] subclass.
 */
class DepartmentDetailsFragment :
    BaseFragment<DepartmentDetailsViewModel>(R.layout.fragment_department_details) {

    override val viewModel by viewModel<DepartmentDetailsViewModel>()
    private val userAdapter by lazy {
        UserAdapter(
            ::itemUserClickHandler,
            ::updateUserClickHandler,
            ::deleteClickHandler
        )
    }
    private val args by navArgs<DepartmentDetailsFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getKoin().setProperty(ViewModelPropertyKey.DEPARTMENT_ID, args.departmentId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRv()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(viewModel) {
            departmentWithStuff.observe(
                this@DepartmentDetailsFragment.viewLifecycleOwner,
                Observer {
                    tvName.initText(it.name)
                    tvDescription.initText(it.description)
                    userAdapter.submitList(it.users)
                }
            )
        }
    }

    override fun <T : BaseNavigation> handleNavigationEvent(navigationEvent: T) {
        when (navigationEvent) {
            DepartmentNavigation.Back -> findNavController().popBackStack()
            is DepartmentNavigation.UpdateDepartment -> {
                findNavController().navigate(
                    DepartmentDetailsFragmentDirections.actionDepartmentDetailsFragmentToCreateDepartmentFragment(
                        navigationEvent
                    )
                )
            }
            is UserNavigation.UpdateUser -> {
                findNavController().navigate(
                    DepartmentDetailsFragmentDirections
                        .actionDepartmentDetailsFragmentToCreateUserFragment(
                            navigationEvent
                        )
                )
            }
            is UserNavigation.Details -> {
                findNavController().navigate(
                    DepartmentDetailsFragmentDirections.actionDepartmentDetailsFragmentToUserDetailsFragment(
                        navigationEvent.userId
                    )
                )
            }
        }
    }

    override fun initToolBar() {
        tvTitle.initText(R.string.details_department)
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
                when (it.itemId) {
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

    private fun initRv() {
        with(rvUsers) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = userAdapter
        }
    }

    private fun updateUserClickHandler(user: UserDomainModel) {
        viewModel.onClickUpdateUser(user)
    }

    private fun deleteClickHandler(user: UserDomainModel) {
        viewModel.onClickDeleteUser(user)
    }

    private fun itemUserClickHandler(user: UserDomainModel) {
        viewModel.onClickItemUser(user)
    }

}
