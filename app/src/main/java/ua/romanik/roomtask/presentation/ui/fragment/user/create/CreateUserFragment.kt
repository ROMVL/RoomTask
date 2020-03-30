package ua.romanik.roomtask.presentation.ui.fragment.user.create

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_create_user.*
import kotlinx.android.synthetic.main.toolbar.*
import org.koin.android.viewmodel.ext.android.viewModel
import ua.romanik.roomtask.R
import ua.romanik.roomtask.domain.model.department.DepartmentDomainModel
import ua.romanik.roomtask.presentation.base.fragment.BaseFragment
import ua.romanik.roomtask.presentation.base.initText
import ua.romanik.roomtask.presentation.base.navigationevent.BaseNavigation
import ua.romanik.roomtask.presentation.base.visible
import ua.romanik.roomtask.presentation.ui.fragment.user.navigation.UserNavigation


class CreateUserFragment : BaseFragment<CreateUserViewModel>(R.layout.fragment_create_user) {

    override val viewModel: CreateUserViewModel by viewModel()
    private val args by navArgs<CreateUserFragmentArgs>()
    private val departmentSpinnerAdapter by lazy {
        ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDepartmentSpinner()
        when (args.navigationEvent) {
            UserNavigation.CreateUser -> {
                tvTitle.initText(R.string.create)
                btnDone.setOnClickListener {
                    viewModel.onClickSaveUser(
                        etName.text.toString(),
                        etEmail.text.toString(),
                        etPhone.text.toString(),
                        spinnerDepartment.selectedItemPosition
                    )
                }
            }
            is UserNavigation.UpdateUser -> {
                tvTitle.initText(R.string.update)
                (args.navigationEvent as UserNavigation.UpdateUser).userDomainModel.let { userModel ->
                    etName.initText(userModel.name)
                    etEmail.initText(userModel.email)
                    etPhone.initText(userModel.phone)
                    btnDone.setOnClickListener {
                        viewModel.onClickUpdateUser(
                            userModel.id,
                            etName.text.toString(),
                            etEmail.text.toString(),
                            etPhone.text.toString(),
                            spinnerDepartment.selectedItemPosition
                        )
                    }
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(viewModel) {
            departments.observe(
                this@CreateUserFragment.viewLifecycleOwner,
                Observer {
                    it.map { model -> model.name }.let { value ->
                        departmentSpinnerAdapter.clear()
                        departmentSpinnerAdapter.addAll(value)
                    }
                    if (args.navigationEvent is UserNavigation.UpdateUser) {
                        (args.navigationEvent as UserNavigation.UpdateUser).userDomainModel.departmentId.let { departmentId ->
                            it.indexOfFirst { departmentDomainModel ->
                                departmentDomainModel.id == departmentId
                            }.let { indexOfSelectedItem ->
                                spinnerDepartment.setSelection(indexOfSelectedItem, true)
                            }
                        }
                    }
                }
            )
        }
    }

    override fun <T : BaseNavigation> handleNavigationEvent(navigationEvent: T) {
        when (navigationEvent) {
            UserNavigation.Back -> findNavController().popBackStack()
        }
    }

    override fun initToolBar() {
        with(btnBackArrow) {
            visible()
            setOnClickListener { findNavController().popBackStack() }
        }
    }

    private fun initDepartmentSpinner() {
        with(spinnerDepartment) {
            adapter = departmentSpinnerAdapter
        }
    }


}
