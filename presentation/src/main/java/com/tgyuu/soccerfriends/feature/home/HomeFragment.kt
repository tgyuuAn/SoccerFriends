package com.tgyuu.soccerfriends.feature.home

import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tgyuu.soccerfriends.R
import com.tgyuu.soccerfriends.common.base.BaseFragment
import com.tgyuu.soccerfriends.common.base.repeatOnStarted
import com.tgyuu.soccerfriends.databinding.FragmentHomeBinding
import com.tgyuu.soccerfriends.feature.splash.SplashViewModel
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
            HomeViewModel.HomeEvent.MoveToScoreBoard -> findNavController().navigate("soccerfriends://score_board_nav".toUri())
            HomeViewModel.HomeEvent.MoveToTeamManagement -> findNavController().navigate("soccerfriends://team_nav".toUri())
            HomeViewModel.HomeEvent.MoveToFormation -> findNavController().navigate("soccerfriends://formation_nav".toUri())
        }
    }
}