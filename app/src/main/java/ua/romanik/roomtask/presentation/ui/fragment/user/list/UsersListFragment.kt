package ua.romanik.roomtask.presentation.ui.fragment.user.list

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.PopupMenu
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_users_list.*
import kotlinx.android.synthetic.main.toolbar.*
import org.koin.android.viewmodel.ext.android.viewModel
import ua.romanik.roomtask.R
import ua.romanik.roomtask.domain.model.user.UserDomainModel
import ua.romanik.roomtask.presentation.base.fragment.BaseFragment
import ua.romanik.roomtask.presentation.base.initText
import ua.romanik.roomtask.presentation.base.navigationevent.BaseNavigation
import ua.romanik.roomtask.presentation.base.visible
import ua.romanik.roomtask.presentation.ui.adapter.user.UserAdapter
import ua.romanik.roomtask.presentation.ui.fragment.user.navigation.UserNavigation


class UsersListFragment : BaseFragment<UsersListViewModel>(R.layout.fragment_users_list) {

    override val viewModel by viewModel<UsersListViewModel>()
    private val userAdapter by lazy {
        UserAdapter(
            ::itemUserClickHandler,
            ::updateUserClickHandler,
            ::updateDeleteClickHandler
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRv()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(viewModel) {
            users.observe(
                this@UsersListFragment.viewLifecycleOwner,
                Observer { userAdapter.submitList(it) }
            )
        }
    }

    override fun initToolBar() {
        tvTitle.initText(R.string.users)
        with(btnMenu) {
            visible()
            setOnClickListener { showPopupMenu(it) }
        }
    }

    override fun <T : BaseNavigation> handleNavigationEvent(navigationEvent: T) {
        when (navigationEvent) {
            is UserNavigation.CreateUser -> {
                findNavController().navigate(
                    UsersListFragmentDirections.actionUsersListFragmentToCreateUserFragment(
                        navigationEvent
                    )
                )
            }
            is UserNavigation.UpdateUser -> {
                findNavController().navigate(
                    UsersListFragmentDirections.actionUsersListFragmentToCreateUserFragment(
                        navigationEvent
                    )
                )
            }
            is UserNavigation.Details -> {
                findNavController().navigate(
                    UsersListFragmentDirections.actionUsersListFragmentToUserDetailsFragment(
                        navigationEvent.userId
                    )
                )
            }
        }
    }

    private fun showPopupMenu(anchor: View) {
        PopupMenu(anchor.context, anchor).apply {
            gravity = Gravity.END
            menuInflater.inflate(R.menu.menu_users, menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.itemCreate -> {
                        viewModel.onClickCreate()
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
        viewModel.onClickUpdate(user)
    }

    private fun updateDeleteClickHandler(user: UserDomainModel) {
        viewModel.onClickDelete(user)
    }

    private fun itemUserClickHandler(user: UserDomainModel) {
        viewModel.onClickUser(user)
    }

}
