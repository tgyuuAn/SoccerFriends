package com.tgyuu.presentation.feature.team

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.tgyuu.domain.entity.Member
import com.tgyuu.domain.entity.Team
import com.tgyuu.presentation.R
import com.tgyuu.presentation.common.base.BaseFragment
import com.tgyuu.presentation.common.base.UiState
import com.tgyuu.presentation.common.base.repeatOnStarted
import com.tgyuu.presentation.databinding.FragmentTeamBinding
import com.tgyuu.presentation.feature.team.bottomsheet.MemberMoreBottomSheetFragment
import com.tgyuu.presentation.feature.team.dialog.ChangeDialogFragment
import com.tgyuu.presentation.feature.team.recyclerview.AdapterViewModel
import com.tgyuu.presentation.feature.team.recyclerview.TeamListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamFragment :
    BaseFragment<FragmentTeamBinding, TeamViewModel>(FragmentTeamBinding::inflate) {
    override val fragmentViewModel: TeamViewModel by viewModels()
    private val adapterViewModel: AdapterViewModel by viewModels()
    private val teamListAdapter: TeamListAdapter by lazy { TeamListAdapter(adapterViewModel) }
    private var changeDialogFragment: ChangeDialogFragment? = null
    private var memberMoreBottomSheetFragment: MemberMoreBottomSheetFragment? = null
    private var imageUri: String = ""
    private var requestCode: RequestCode? = null

    sealed class RequestCode {
        object TeamImage : RequestCode()
        object MemberImage : RequestCode()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setStatusBarAndIconColor(R.color.main, StatusBarIconColor.WHITE)
        setDialogFragmentResultListner()
        setBottomSheetFragmentResultListner()
        setRecyclerView()

        binding.viewModel = fragmentViewModel.apply {
            repeatOnStarted {
                eventFlow.collect { handleEvent(it) }
            }

            repeatOnStarted {
                memberListFlow.collect { handleMemberListState(it) }
            }

            repeatOnStarted {
                team.collect { handleTeamState(it) }
            }
            getMemberList()
            getTeam()
        }

        adapterViewModel.apply {
            repeatOnStarted {
                eventFlow.collect { handleAdapterEvent(it) }
            }
        }
    }

    private fun setDialogFragmentResultListner() {
        requireActivity().supportFragmentManager.setFragmentResultListener(
            ChangeDialogFragment.TAG,
            viewLifecycleOwner,
        ) { _, bundle ->
            val teamFlag = bundle.getString(ChangeDialogFragment.Team)
            if (teamFlag != null) {
                fragmentViewModel.getTeam()
                return@setFragmentResultListener
            }

            val memberFlag = bundle.getString(ChangeDialogFragment.Member)
            if (memberFlag != null) {
                fragmentViewModel.getMemberList()
                return@setFragmentResultListener
            }
        }
    }

    private fun setBottomSheetFragmentResultListner() {
        requireActivity().supportFragmentManager.setFragmentResultListener(
            MemberMoreBottomSheetFragment.TAG,
            viewLifecycleOwner,
        ) { _, bundle ->
            val flag = bundle.getInt(MemberMoreBottomSheetFragment.TAG)
            handleBottomSheetFlag(flag)
        }
    }

    private fun handleBottomSheetFlag(flag: Int) {
        when (flag) {
            MemberMoreBottomSheetFragment.BottomSheetFlag.CHANGE_NICKNAME.value -> changeNickName()
            MemberMoreBottomSheetFragment.BottomSheetFlag.CHANGE_IMAGE.value -> changeImage()
            MemberMoreBottomSheetFragment.BottomSheetFlag.REMOVE_IMAGE.value -> removeImage()
            MemberMoreBottomSheetFragment.BottomSheetFlag.CHANGE_POSITION.value -> changePosition()
            MemberMoreBottomSheetFragment.BottomSheetFlag.CHANGE_BACKNUMBER.value -> changeBackNumber()
            MemberMoreBottomSheetFragment.BottomSheetFlag.REMOVE_MEMBER.value -> removeMember()
            else -> Unit
        }
    }

    private fun changeNickName() {
        if (fragmentViewModel.updateMember != null) {
            showChangeDialog(
                ChangeDialogFragment.DialogType.ChangeMemberName(
                    fragmentViewModel.updateMember!!
                )
            )
        }
    }

    private fun changeImage() {
        navigateToGallery(RequestCode.MemberImage)
    }

    private fun changePosition() {
        if (fragmentViewModel.updateMember != null) {
            showChangeDialog(ChangeDialogFragment.DialogType.ChangePosition(fragmentViewModel.updateMember!!))
        }
    }

    private fun changeBackNumber() {
        if (fragmentViewModel.updateMember != null) {
            showChangeDialog(ChangeDialogFragment.DialogType.ChangeNumber(fragmentViewModel.updateMember!!))
        }
    }

    private fun removeImage() {
        fragmentViewModel.removeMemberImage()
    }

    private fun removeMember() {
        fragmentViewModel.removeMember()
    }

    private fun handleEvent(event: TeamViewModel.TeamEvent) {
        when (event) {
            TeamViewModel.TeamEvent.AddMember -> findNavController().navigate(R.id.action_global_addMemberFragment)
            TeamViewModel.TeamEvent.ChangeTeamName -> showChangeDialog(ChangeDialogFragment.DialogType.ChangeTeamName)
            TeamViewModel.TeamEvent.ChangeTeamImage -> navigateToGallery(RequestCode.TeamImage)
        }
    }

    private fun showChangeDialog(dialogType: ChangeDialogFragment.DialogType) {
        if (changeDialogFragment == null) {
            changeDialogFragment =
                ChangeDialogFragment(
                    dialogType = dialogType,
                    callBack = { changeDialogFragment = null })

            changeDialogFragment!!.show(
                requireActivity().supportFragmentManager,
                ChangeDialogFragment.TAG
            )
        }
    }

    private fun navigateToGallery(code: RequestCode) {
        requestCode = code
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }
        activityResult.launch(intent)
    }

    private val activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK && it.data != null) {
            imageUri = it.data!!.data.toString()

            when (requestCode!!) {
                RequestCode.TeamImage -> fragmentViewModel.updateTeamImage(imageUri)
                RequestCode.MemberImage -> fragmentViewModel.updateMemberImage(imageUri)
            }
        }
    }

    private fun handleMemberListState(uiState: UiState<List<Member>>) {
        when (uiState) {
            UiState.Loading -> showLoadingScreen()
            is UiState.Success -> {
                hideLoadingScreen()
                updateMemberList(uiState.data)
            }

            is UiState.Error -> toast("멤버 정보 갱신에 실패하였습니다.")
        }
    }

    private fun showLoadingScreen() = binding.apply {
        teamLoadingView.visibility = View.VISIBLE
        teamLTV.visibility = View.VISIBLE
    }

    private fun hideLoadingScreen() = binding.apply {
        teamLoadingView.visibility = View.GONE
        teamLTV.visibility = View.GONE
    }

    private fun updateMemberList(memberList: List<Member>) {
        teamListAdapter.submitList(memberList.toList())
        binding.totalTeamTV.text = "* 총 팀원 수 : " + memberList.size.toString()
    }

    private fun handleTeamState(teamState: UiState<Team>) {
        when (teamState) {
            UiState.Init -> {}
            UiState.Loading -> showLoadingScreen()
            is UiState.Success -> {
                hideLoadingScreen()
                updateTeam(teamState.data)
            }

            is UiState.Error -> toast("팀 정보 갱신에 실패하였습니다.")
        }
    }

    private fun updateTeam(team: Team) {
        binding.teamNameTV.text = team.name

        if (team.image.isEmpty()) {
            binding.teamLogoIV.setImageResource(R.drawable.circle)
            return
        }

        Glide.with(requireContext())
            .load(team.image)
            .circleCrop()
            .into(binding.teamLogoIV)
    }

    private fun handleAdapterEvent(event: AdapterViewModel.AdapterEvent) {
        when (event) {
            is AdapterViewModel.AdapterEvent.ClickMore -> {
                fragmentViewModel.updateMember = event.member
                showMemberMoreBottomSheet()
            }
        }
    }

    private fun showMemberMoreBottomSheet() {
        if (memberMoreBottomSheetFragment == null) {
            memberMoreBottomSheetFragment =
                MemberMoreBottomSheetFragment(callBack = { memberMoreBottomSheetFragment = null })

            memberMoreBottomSheetFragment!!.show(
                requireActivity().supportFragmentManager,
                MemberMoreBottomSheetFragment.TAG
            )
        }
    }

    private fun setRecyclerView() =
        binding.teamListRV.apply {
            adapter = teamListAdapter
            layoutManager = LinearLayoutManager(requireActivity())
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
}