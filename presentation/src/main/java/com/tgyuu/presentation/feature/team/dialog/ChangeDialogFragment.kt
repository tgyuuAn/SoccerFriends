package com.tgyuu.presentation.feature.team.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tgyuu.presentation.common.base.UiState
import com.tgyuu.presentation.common.base.repeatOnStarted
import com.tgyuu.presentation.databinding.FragmentChangeDialogBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeDialogFragment(private val dismissCallBack: () -> Unit) : DialogFragment() {

    companion object {
        const val TAG = "ChangeDialogFragment"
    }

    private val fragmentViewModel: ChangeDialogViewModel by viewModels()
    private var _binding: FragmentChangeDialogBinding? = null
    val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireActivity()).apply {
            _binding = FragmentChangeDialogBinding.inflate(layoutInflater, null, false)
            setView(onCreateView(layoutInflater, binding.rootLayout, savedInstanceState))
        }.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBinding()
    }

    private fun setBinding() = binding.apply {
        viewModel = fragmentViewModel.apply {
            viewLifecycleOwner.apply {
                repeatOnStarted { eventFlow.collect { handleEvent(it) } }
                repeatOnStarted { teamName.collect { handleTeamNameUiState(it) } }
            }
        }
    }

    private fun handleEvent(event: ChangeDialogViewModel.DialogEvent) {
        when (event) {
            ChangeDialogViewModel.DialogEvent.Cancel -> dismiss()
            ChangeDialogViewModel.DialogEvent.ClickComplete -> {
                fragmentViewModel.changeTeamName(binding.newTeamNameEDT.text.toString())
            }
        }
    }

    private fun handleTeamNameUiState(uiState: UiState<String>){
        when(uiState){
            UiState.Loading -> {}
            is UiState.Success -> {}
            is UiState.Error -> {}
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        dismissCallBack()
    }
}