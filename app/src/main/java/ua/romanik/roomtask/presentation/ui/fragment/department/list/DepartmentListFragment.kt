package ua.romanik.roomtask.presentation.ui.fragment.department.list

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.PopupMenu
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_department_list.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel

import ua.romanik.roomtask.R
import ua.romanik.roomtask.domain.model.department.DepartmentDomainModel
import ua.romanik.roomtask.presentation.base.fragment.BaseFragment
import ua.romanik.roomtask.presentation.base.initText
import ua.romanik.roomtask.presentation.base.navigationevent.BaseNavigation
import ua.romanik.roomtask.presentation.base.visible
import ua.romanik.roomtask.presentation.ui.adapter.department.DepartmentAdapter
import ua.romanik.roomtask.presentation.ui.fragment.department.navigation.DepartmentNavigation

@ExperimentalCoroutinesApi
class DepartmentListFragment : BaseFragment<DepartmentListViewModel>(R.layout.fragment_department_list) {

    override val viewModel by viewModel<DepartmentListViewModel>()
    private val departmentAdapter by lazy {
        DepartmentAdapter(
            ::onClickDeleteDepartmentHandler,
            ::onClickUpdateDepartmentHandler
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRv()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(viewModel) {
            departments.observe(
                this@DepartmentListFragment.viewLifecycleOwner,
                Observer { departmentAdapter.submitList(it) }
            )
        }
    }

    override fun <T : BaseNavigation> handleNavigationEvent(navigationEvent: T) {
        findNavController().navigate(
            DepartmentListFragmentDirections.actionDepartmentListFragmentToCreateDepartmentFragment(navigationEvent)
        )
    }

    private fun initRv() {
        with(rvDepartments) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = departmentAdapter
        }
    }

    override fun initToolBar() {
        tvTitle.initText(R.string.departments)
        with(btnMenu) {
            visible()
            setOnClickListener { showPopupMenu(it) }
        }
    }

    private fun showPopupMenu(anchor: View) {
        PopupMenu(anchor.context, anchor).apply {
            gravity = Gravity.END
            menuInflater.inflate(R.menu.menu_departments, menu)
            setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.itemCreate -> {
                        viewModel.onClickCreate()
                        true
                    }
                    else -> false
                }
            }
        }.show()
    }

    private fun onClickUpdateDepartmentHandler(departmentDomainModel: DepartmentDomainModel) {
        viewModel.onClickUpdate(departmentDomainModel)
    }

    private fun onClickDeleteDepartmentHandler(departmentDomainModel: DepartmentDomainModel) {
        viewModel.onClickDeleteDepartment(departmentDomainModel)
    }

}
