package com.tgyuu.presentation.feature.team.dialog

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tgyuu.domain.entity.Member
import com.tgyuu.presentation.R
import com.tgyuu.presentation.common.base.UiState
import com.tgyuu.presentation.common.base.repeatOnStarted
import com.tgyuu.presentation.databinding.FragmentChangeDialogBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeDialogFragment(private val dialogType: DialogType, private val callBack: () -> Unit) :
    DialogFragment() {

    companion object {
        const val TAG = "ChangeDialogFragment"
        const val Member = "Member"
        const val Team = "Team"
    }

    sealed class DialogType {
        data class ChangeMemberName(val member: Member) : DialogType()
        data class ChangePosition(val member: Member) : DialogType()
        data class ChangeNumber(val member: Member) : DialogType()
        object ChangeTeamName : DialogType()
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
        setDialogByDialogType()
        setBinding()
    }

    private fun setDialogByDialogType() {
        val titleHintPair: Pair<String, String> =
            when (dialogType) {
                is DialogType.ChangeNumber -> requireContext().getString(R.string.changeBackNumber) to requireContext().getString(
                    R.string.changeBackNumberHint
                )

                is DialogType.ChangeMemberName -> requireContext().getString(R.string.changeNickName) to requireContext().getString(
                    R.string.changeNickNameHint
                )

                is DialogType.ChangePosition -> requireContext().getString(R.string.changePosition) to requireContext().getString(
                    R.string.changePositionHint
                )

                DialogType.ChangeTeamName -> requireContext().getString(R.string.changeTeamName) to requireContext().getString(
                    R.string.changeTeamNameHint
                )
            }

        binding.dialogTitleTV.text = titleHintPair.first
        binding.changeValueEDT.hint = titleHintPair.second
    }

    private fun setBinding() = binding.apply {
        viewModel = fragmentViewModel.apply {
            viewLifecycleOwner.apply {
                repeatOnStarted { eventFlow.collect { handleEvent(it) } }
                repeatOnStarted { team.collect { handleTeamNameUiState(it) } }
                repeatOnStarted { member.collect { handleMemberUiState(it) } }
            }
        }
    }

    private fun handleEvent(event: ChangeDialogViewModel.DialogEvent) {
        when (event) {
            ChangeDialogViewModel.DialogEvent.Cancel -> dismiss()
            ChangeDialogViewModel.DialogEvent.ClickComplete -> clickCompleteByDialogType()
        }
    }

    private fun clickCompleteByDialogType() {
        when (dialogType) {
            is DialogType.ChangeNumber -> fragmentViewModel.updateMemberNumber(
                dialogType.member,
                binding.changeValueEDT.text.toString()
            )

            is DialogType.ChangeMemberName -> fragmentViewModel.updateMemberName(
                dialogType.member,
                binding.changeValueEDT.text.toString()
            )

            is DialogType.ChangePosition -> fragmentViewModel.updateMemberPosition(
                dialogType.member,
                binding.changeValueEDT.text.toString()
            )

            DialogType.ChangeTeamName -> fragmentViewModel.changeTeamName(binding.changeValueEDT.text.toString())
        }
    }

    private fun handleTeamNameUiState(uiState: UiState<Unit>) {
        when (uiState) {
            UiState.Loading -> {
                //Lottie
            }

            is UiState.Success -> {
                setFragmentResult(
                    TAG,
                    bundleOf(Team to ""),
                )
                dismiss()
            }

            is UiState.Error -> toast("최소 한 글자 이상의 팀 명으로 설정해주세요!")
        }
    }

    private fun handleMemberUiState(uiState: UiState<Unit>) {
        when (uiState) {
            UiState.Loading -> {
                //Lottie
            }

            is UiState.Success -> {
                setFragmentResult(
                    TAG,
                    bundleOf(Member to ""),
                )
                dismiss()
            }

            is UiState.Error -> toast(uiState.message)
        }
    }

    private fun toast(str: String) =
        Toast.makeText(requireContext(), str, Toast.LENGTH_SHORT).show()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        callBack()
    }
}