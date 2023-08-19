package com.tgyuu.soccerfriends.feature.team.addmember

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tgyuu.soccerfriends.R
import com.tgyuu.soccerfriends.common.base.BaseFragment
import com.tgyuu.soccerfriends.common.base.UiState
import com.tgyuu.soccerfriends.common.base.repeatOnStarted
import com.tgyuu.soccerfriends.databinding.FragmentAddMemberBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddMemberFragment :
    BaseFragment<FragmentAddMemberBinding, AddMemberViewModel>(FragmentAddMemberBinding::inflate) {
    override val fragmentViewModel: AddMemberViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setStatusBarAndIconColor(R.color.main, StatusBarIconColor.WHITE)

        binding.viewModel = fragmentViewModel.apply {
            repeatOnStarted { eventFlow.collect { handleEvent(it) } }
            repeatOnStarted { addMemberState.collect { handleAddMemberState(it) } }
        }
    }

    private fun handleEvent(event: AddMemberViewModel.AddMemberEvent) {
        when (event) {
            AddMemberViewModel.AddMemberEvent.ClickReset -> resetPage()
            AddMemberViewModel.AddMemberEvent.ClickComplete -> addNewMember()
        }
    }

    private fun handleAddMemberState(addMemberState: UiState<Unit>) {
        when (addMemberState) {
            UiState.Loading -> {}
            is UiState.Success -> findNavController().popBackStack()
            is UiState.Error -> {}
        }
    }

    private fun resetPage() = binding.apply {
        newMemberIV.background = ContextCompat.getDrawable(requireContext(), R.drawable.circle)
        newMemberNameEDT.text = null
        newMemberPositionEDT.text = null
        newMemberBackNumberEDT.text = null
    }

    private fun addNewMember() = binding.apply {
        val newMemberName = newMemberNameEDT.text.toString()
        val newMemberBackNumber = newMemberBackNumberEDT.text.toString()
        val newMemberPosition = newMemberPositionEDT.text.toString()
        val isBenchWarmer = reserveCheckCB.isChecked

        fragmentViewModel.addNewMember(
            newMemberName = newMemberName,
            newMemberBackNumber = newMemberBackNumber,
            newMemberPosition = newMemberPosition,
            isBenchWarmer = isBenchWarmer
        )
    }
}