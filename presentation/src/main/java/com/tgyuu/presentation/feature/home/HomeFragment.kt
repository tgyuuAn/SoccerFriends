package com.tgyuu.presentation.feature.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import com.tgyuu.presentation.R
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
            HomeViewModel.HomeEvent.MoveToScoreBoard -> navigateWithUriNavOptions(
                "soccerfriends://score_board_nav",NavOptions.Builder().setPopUpTo(R.id.homeFragment,false).build()
            )
            HomeViewModel.HomeEvent.MoveToTeamManagement -> navigateWithUriNavOptions("soccerfriends://team_nav",NavOptions.Builder().setPopUpTo(R.id.homeFragment,false).build())
            HomeViewModel.HomeEvent.MoveToFormation -> navigateWithUriNavOptions("soccerfriends://formation_nav",NavOptions.Builder().setPopUpTo(R.id.homeFragment,false).build())
        }
    }
}