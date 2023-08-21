package com.tgyuu.presentation.feature.team.addmember

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.tgyuu.presentation.R
import com.tgyuu.presentation.common.base.BaseFragment
import com.tgyuu.presentation.common.base.UiState
import com.tgyuu.presentation.common.base.repeatOnStarted
import com.tgyuu.presentation.databinding.FragmentAddMemberBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddMemberFragment :
    BaseFragment<FragmentAddMemberBinding, AddMemberViewModel>(FragmentAddMemberBinding::inflate) {
    override val fragmentViewModel: AddMemberViewModel by viewModels()
    private var imageUri: String = ""

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
            AddMemberViewModel.AddMemberEvent.ClickImage -> navigateToGallery()
        }
    }

    private fun navigateToGallery() {
        val intenet = Intent(Intent.ACTION_GET_CONTENT)
        intenet.type = "image/*"
        activityResult.launch(intenet)
    }

    private val activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK && it.data != null) {
            imageUri = it.data!!.data.toString()

            Glide.with(requireContext())
                .load(imageUri)
                .circleCrop()
                .into(binding.newMemberIV)
        }
    }

    private fun handleAddMemberState(addMemberState: UiState<Unit>) {
        when (addMemberState) {
            UiState.Init -> {}
            UiState.Loading -> showLoadingScreen()
            is UiState.Success -> {
                hideLoadingScreen()
                findNavController().popBackStack()
            }
            is UiState.Error -> {}
        }
    }

    private fun showLoadingScreen() = binding.apply {
        addMemberLoadingView.visibility = View.VISIBLE
        addMemberLTV.visibility = View.VISIBLE
    }

    private fun hideLoadingScreen() = binding.apply {
        addMemberLoadingView.visibility = View.GONE
        addMemberLTV.visibility = View.GONE
    }

    private fun resetPage() = binding.apply {
        newMemberIV.background = ContextCompat.getDrawable(requireContext(), R.drawable.circle)
        newMemberNameEDT.text = null
        newMemberPositionEDT.text = null
        newMemberBackNumberEDT.text = null
        reserveCheckCB.setChecked(false)
    }

    private fun addNewMember() = binding.apply {
        val newMemberName = newMemberNameEDT.text.toString()
        val newMemberBackNumber = newMemberBackNumberEDT.text.toString()
        val newMemberPosition = newMemberPositionEDT.text.toString()
        val isBenchWarmer = reserveCheckCB.isChecked
        val newMemberImage = if (imageUri.isEmpty()) ""
        else imageUri

        fragmentViewModel.addNewMember(
            newMemberImage = newMemberImage,
            newMemberName = newMemberName,
            newMemberBackNumber = newMemberBackNumber,
            newMemberPosition = newMemberPosition,
            isBenchWarmer = isBenchWarmer
        )
    }
}