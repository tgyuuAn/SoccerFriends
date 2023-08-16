package com.tgyuu.soccerfriends.feature.scoreboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.tgyuu.soccerfriends.R
import com.tgyuu.soccerfriends.common.base.BaseFragment
import com.tgyuu.soccerfriends.common.base.repeatOnStarted
import com.tgyuu.soccerfriends.databinding.FragmentScoreBoardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScoreBoardFragment :
    BaseFragment<FragmentScoreBoardBinding, ScoreBoardViewModel>(FragmentScoreBoardBinding::inflate) {
    override val fragmentViewModel: ScoreBoardViewModel by viewModels()

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

    private fun handleEvent(event: ScoreBoardViewModel.ScoreBoardEvent) {
        when (event) {
            ScoreBoardViewModel.ScoreBoardEvent.ClickButton -> binding.apply {
                if (expandableLayout.isExpanded) expandableLayout.collapse()
                else expandableLayout.expand()
            }
        }
    }
}