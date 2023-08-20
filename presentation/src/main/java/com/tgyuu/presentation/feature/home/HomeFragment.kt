package com.tgyuu.presentation.feature.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.tgyuu.presentation.common.base.BaseFragment
import com.tgyuu.presentation.common.base.repeatOnStarted
import com.tgyuu.presentation.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeViewModel>(FragmentHomeBinding::inflate) {
    override val fragmentViewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewModel = fragmentViewModel.apply {
                repeatOnStarted {
                    eventFlow.collect { handleEvent(it) }
                }
            }
        }
    }

    private fun handleEvent(event: HomeViewModel.HomeEvent) {
        when (event) {
            HomeViewModel.HomeEvent.MoveToScoreBoard -> navigateWithUri("soccerfriends://score_board_nav")
            HomeViewModel.HomeEvent.MoveToTeamManagement -> navigateWithUri("soccerfriends://team_nav")
            HomeViewModel.HomeEvent.MoveToFormation -> navigateWithUri("soccerfriends://formation_nav")
        }
    }
}