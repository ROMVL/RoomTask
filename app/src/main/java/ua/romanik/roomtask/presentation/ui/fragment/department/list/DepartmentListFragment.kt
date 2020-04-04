package ua.romanik.roomtask.presentation.ui.fragment.department.list

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.PopupMenu
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
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
import ua.romanik.roomtask.presentation.base.visibleIf
import ua.romanik.roomtask.presentation.ui.adapter.department.DepartmentAdapter
import ua.romanik.roomtask.presentation.ui.common.PaddingItemDecorator
import ua.romanik.roomtask.presentation.ui.fragment.department.navigation.DepartmentNavigation

@ExperimentalCoroutinesApi
class DepartmentListFragment :
    BaseFragment<DepartmentListViewModel>(R.layout.fragment_department_list) {

    override val viewModel by viewModel<DepartmentListViewModel>()
    private val departmentAdapter by lazy {
        DepartmentAdapter(
            ::onClickItemDepartmentHandler,
            ::onClickDeleteDepartmentHandler,
            ::onClickUpdateDepartmentHandler
        )
    }
    private val departmentItemDecorator by lazy {
        PaddingItemDecorator(
            requireContext(),
            R.dimen.department_item_margin_edge,
            R.dimen.department_item_margin_inner,
            R.dimen.department_item_margin_inner_opposite
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
        when (navigationEvent) {
            is DepartmentNavigation.Details -> {
                findNavController().navigate(
                    DepartmentListFragmentDirections.actionDepartmentListFragmentToDepartmentDetailsFragment(
                        navigationEvent.departmentId
                    )
                )
            }
            DepartmentNavigation.CreateDepartment -> {
                findNavController().navigate(
                    DepartmentListFragmentDirections.actionDepartmentListFragmentToCreateDepartmentFragment(
                        navigationEvent
                    )
                )
            }
            is DepartmentNavigation.UpdateDepartment -> {
                findNavController().navigate(
                    DepartmentListFragmentDirections.actionDepartmentListFragmentToCreateDepartmentFragment(
                        navigationEvent
                    )
                )
            }
        }

    }

    private fun initRv() {
        with(rvDepartments) {
            adapter = departmentAdapter
            addItemDecoration(departmentItemDecorator)
        }
    }

    override fun initToolBar() {
        tvTitle.initText(R.string.departments)
        with(btnMenu) {
            visible()
            setOnClickListener { showPopupMenu(it) }
        }
    }

    override fun handleLoadingStateEvent(state: Boolean) {
        progressBar.visibleIf { state }
    }

    private fun showPopupMenu(anchor: View) {
        PopupMenu(anchor.context, anchor).apply {
            gravity = Gravity.END
            menuInflater.inflate(R.menu.menu_departments, menu)
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

    private fun onClickUpdateDepartmentHandler(departmentDomainModel: DepartmentDomainModel) {
        viewModel.onClickUpdate(departmentDomainModel)
    }

    private fun onClickDeleteDepartmentHandler(departmentDomainModel: DepartmentDomainModel) {
        viewModel.onClickDeleteDepartment(departmentDomainModel)
    }

    private fun onClickItemDepartmentHandler(departmentDomainModel: DepartmentDomainModel) {
        viewModel.onClickDepartmentItem(departmentDomainModel)
    }

}
