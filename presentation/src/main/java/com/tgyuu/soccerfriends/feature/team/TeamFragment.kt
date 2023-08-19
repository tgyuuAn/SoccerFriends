package com.tgyuu.soccerfriends.feature.team

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tgyuu.soccerfriends.R
import com.tgyuu.soccerfriends.common.base.BaseFragment
import com.tgyuu.soccerfriends.common.base.repeatOnStarted
import com.tgyuu.soccerfriends.databinding.FragmentTeamBinding
import com.tgyuu.soccerfriends.feature.formation.FormationViewModel
import com.tgyuu.soccerfriends.feature.team.recyclerview.TeamListAdapter

class TeamFragment :
    BaseFragment<FragmentTeamBinding, TeamViewModel>(FragmentTeamBinding::inflate) {
    override val fragmentViewModel: TeamViewModel by viewModels()
    private val teamListAdapter: TeamListAdapter by lazy { TeamListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setStatusBarAndIconColor(R.color.main, StatusBarIconColor.WHITE)

        binding.viewModel = fragmentViewModel.apply {
            repeatOnStarted {
                eventFlow.collect { handleEvent(it) }
            }
        }
        setRecyclerView()
    }

    private fun handleEvent(event: TeamViewModel.TeamEvent) {
        when (event) {
            TeamViewModel.TeamEvent.AddMember -> findNavController().navigate(R.id.action_global_addMemberFragment)
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