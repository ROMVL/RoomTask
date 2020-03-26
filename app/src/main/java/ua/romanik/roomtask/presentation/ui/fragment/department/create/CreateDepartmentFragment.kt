package ua.romanik.roomtask.presentation.ui.fragment.department.create

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_create_department.*
import kotlinx.android.synthetic.main.toolbar.*
import org.koin.android.viewmodel.ext.android.viewModel
import ua.romanik.roomtask.R
import ua.romanik.roomtask.presentation.base.fragment.BaseFragment
import ua.romanik.roomtask.presentation.base.initText
import ua.romanik.roomtask.presentation.base.navigationevent.BaseNavigation
import ua.romanik.roomtask.presentation.base.visible
import ua.romanik.roomtask.presentation.ui.fragment.department.navigation.DepartmentNavigation

class CreateDepartmentFragment : BaseFragment<CreateDepartmentViewModel>(R.layout.fragment_create_department) {

    override val viewModel by viewModel<CreateDepartmentViewModel>()
    private val args by navArgs<CreateDepartmentFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when (args.navigation) {
            DepartmentNavigation.CreateDepartment -> {
                tvTitle.initText(R.string.create_department)
                btnDone.setOnClickListener {
                    viewModel.onClickCreate(etName.text.toString(), etDescription.text.toString())
                }
            }
            is DepartmentNavigation.UpdateDepartment -> {
                tvTitle.initText(R.string.update_department)
                (args.navigation as DepartmentNavigation.UpdateDepartment).departmentDomainModel.let { department ->
                    etName.initText(department.name)
                    etDescription.initText(department.description)
                    btnDone.setOnClickListener {
                        viewModel.onClickUpdate(department.id, etName.text.toString(), etDescription.text.toString())
                    }
                }
            }
        }
    }

    override fun <T : BaseNavigation> handleNavigationEvent(navigationEvent: T) {
        when (navigationEvent) {
            DepartmentNavigation.Back -> findNavController().popBackStack()
        }
    }

    override fun initToolBar() {
        with(btnBackArrow) {
            visible()
            setOnClickListener { findNavController().popBackStack() }
        }
    }

}
