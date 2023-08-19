package com.tgyuu.soccerfriends.feature.team.addmember

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.tgyuu.soccerfriends.R
import com.tgyuu.soccerfriends.common.base.BaseFragment
import com.tgyuu.soccerfriends.common.base.repeatOnStarted
import com.tgyuu.soccerfriends.databinding.FragmentAddMemberBinding


class AddMemberFragment :
    BaseFragment<FragmentAddMemberBinding, AddMemberViewModel>(FragmentAddMemberBinding::inflate) {
    override val fragmentViewModel: AddMemberViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setStatusBarAndIconColor(R.color.main, StatusBarIconColor.WHITE)

        binding.viewModel = fragmentViewModel.apply {
            repeatOnStarted { eventFlow.collect { handleEvent(it) } }
        }
    }

    private fun handleEvent(event: AddMemberViewModel.AddMemberEvent) {
        when (event) {
            AddMemberViewModel.AddMemberEvent.ClickReset -> resetPage()
            AddMemberViewModel.AddMemberEvent.ClickComplete -> {}
        }
    }

    private fun resetPage() = binding.apply {
        newMemberIV.background = ContextCompat.getDrawable(requireContext(), R.drawable.circle)
        newMemberNameEDT.text = null
        newMemberPositionEDT.text = null
        newMemberBackNumberEDT.text = null
    }
}