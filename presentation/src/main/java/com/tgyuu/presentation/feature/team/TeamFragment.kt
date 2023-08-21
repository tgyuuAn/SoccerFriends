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
    private var imageUri: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setStatusBarAndIconColor(R.color.main, StatusBarIconColor.WHITE)

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
        }

        adapterViewModel.apply {
            repeatOnStarted {
                eventFlow.collect { handleAdapterEvent(it) }
            }
        }
        setRecyclerView()
    }

    private fun handleEvent(event: TeamViewModel.TeamEvent) {
        when (event) {
            TeamViewModel.TeamEvent.AddMember -> findNavController().navigate(R.id.action_global_addMemberFragment)
            TeamViewModel.TeamEvent.ChangeTeamName -> showChangeTeamNameDialog()
            TeamViewModel.TeamEvent.ChangeTeamImage -> navigateToGallery()
        }
    }

    private fun showChangeTeamNameDialog() {
        if (changeDialogFragment == null) {
            changeDialogFragment = ChangeDialogFragment {
                changeDialogFragment = null
            }
            changeDialogFragment!!.show(
                requireActivity().supportFragmentManager,
                ChangeDialogFragment.TAG
            )
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
                .into(binding.teamLogoIV)
        }
    }

    private fun handleMemberListState(uiState: UiState<List<Member>>) {
        when (uiState) {
            UiState.Loading -> loadingMemberList()
            is UiState.Success -> updateMemberList(uiState.data)
            is UiState.Error -> Unit
        }
    }

    private fun loadingMemberList() {
        //Lottie
    }

    private fun updateMemberList(memberList: List<Member>) {
        teamListAdapter.submitList(memberList.toList())
        binding.totalTeamTV.text = "* 총 팀원 수 : " + memberList.size.toString()
    }

    private fun handleTeamState(teamState: UiState<Team>) {
        when (teamState) {
            UiState.Loading -> {}
            is UiState.Success -> {}
            is UiState.Error -> {}
        }
    }

    private fun handleAdapterEvent(event: AdapterViewModel.AdapterEvent) {
        when (event) {
            is AdapterViewModel.AdapterEvent.ClickMore -> log("클릭!" + event.member.toString())
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