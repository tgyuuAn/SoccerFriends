package com.tgyuu.soccerfriends.feature.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tgyuu.soccerfriends.R
import com.tgyuu.soccerfriends.common.base.BaseFragment
import com.tgyuu.soccerfriends.common.base.repeatOnStarted
import com.tgyuu.soccerfriends.databinding.FragmentSplashBinding
import com.tgyuu.soccerfriends.feature.scoreboard.ScoreBoardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment :
    BaseFragment<FragmentSplashBinding, SplashViewModel>(FragmentSplashBinding::inflate) {

    override val fragmentViewModel: SplashViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setStatusBarAndIconColor(R.color.main, StatusBarIconColor.WHITE)

        binding.apply {
            viewModel = fragmentViewModel.apply {
                repeatOnStarted {
                    eventFlow.collect { handleEvent(it) }
                }
            }
        }
    }

    private fun handleEvent(event: SplashViewModel.SplashEvent) {
        when (event) {
            SplashViewModel.SplashEvent.Splash -> findNavController().navigate(R.id.action_global_home_nav)
            }
        }
    }
}
