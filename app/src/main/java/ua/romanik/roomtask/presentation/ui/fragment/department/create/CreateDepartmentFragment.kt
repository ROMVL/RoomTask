package ua.romanik.roomtask.presentation.ui.fragment.department.create

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_create_department.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.ext.android.viewModel
import ua.romanik.roomtask.R
import ua.romanik.roomtask.domain.model.room.RoomDomainModel
import ua.romanik.roomtask.presentation.base.fragment.BaseFragment
import ua.romanik.roomtask.presentation.base.initText
import ua.romanik.roomtask.presentation.base.navigationevent.BaseNavigation
import ua.romanik.roomtask.presentation.base.visible
import ua.romanik.roomtask.presentation.ui.fragment.department.navigation.DepartmentNavigation
import ua.romanik.roomtask.presentation.ui.fragment.room.navigation.RoomNavigation

class CreateDepartmentFragment : BaseFragment<CreateDepartmentViewModel>(R.layout.fragment_create_department) {

    override val viewModel by viewModel<CreateDepartmentViewModel>()
    private val args by navArgs<CreateDepartmentFragmentArgs>()
    private val roomSpinnerAdapter by lazy {
        ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDepartmentSpinner()
        when (args.navigation) {
            DepartmentNavigation.CreateDepartment -> {
                tvTitle.initText(R.string.create_department)
                btnDone.setOnClickListener {
                    viewModel.onClickCreate(
                        etName.text.toString(),
                        etDescription.text.toString(),
                        spinnerRoom.selectedItemPosition
                    )
                }
            }
            is DepartmentNavigation.UpdateDepartment -> {
                tvTitle.initText(R.string.update_department)
                (args.navigation as? DepartmentNavigation.UpdateDepartment)?.departmentDomainModel?.let { department ->
                    etName.initText(department.name)
                    etDescription.initText(department.description)
                    btnDone.setOnClickListener {
                        viewModel.onClickUpdate(
                            department.id,
                            etName.text.toString(),
                            etDescription.text.toString(),
                            spinnerRoom.selectedItemPosition
                        )
                    }
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(viewModel) {
            rooms.observe(
                this@CreateDepartmentFragment.viewLifecycleOwner,
                Observer { updateRoomSpinner(it) }
            )
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

    private fun updateRoomSpinner(rooms: List<RoomDomainModel>) {
        rooms.map { model -> model.number.toString() }.let { value ->
            roomSpinnerAdapter.clear()
            roomSpinnerAdapter.addAll(value)
        }
        (args.navigation as? DepartmentNavigation.UpdateDepartment)?.departmentDomainModel?.roomId?.let { roomId ->
            rooms.indexOfFirst { roomDomainModel ->
                roomDomainModel.id == roomId
            }.let { indexOfSelectedItem ->
                spinnerRoom.setSelection(indexOfSelectedItem, true)
            }
        }
    }

    private fun initDepartmentSpinner() {
        spinnerRoom.adapter = roomSpinnerAdapter
    }
}
