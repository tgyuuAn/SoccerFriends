package com.tgyuu.presentation.feature.scoreboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.tgyuu.presentation.R
import com.tgyuu.presentation.common.base.BaseFragment
import com.tgyuu.presentation.common.base.repeatOnStarted
import com.tgyuu.presentation.databinding.FragmentScoreBoardBinding
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
            ScoreBoardViewModel.ScoreBoardEvent.ClickButton -> handleExpandableLayout()
            ScoreBoardViewModel.ScoreBoardEvent.ClickPlusPlayTime -> {}
            ScoreBoardViewModel.ScoreBoardEvent.ClickMinusPlayTime -> {}
            ScoreBoardViewModel.ScoreBoardEvent.ClickPlusAlarmTime -> {}
            ScoreBoardViewModel.ScoreBoardEvent.ClickMinusAlarmTime -> {}
        }
    }

    private fun handleExpandableLayout() = binding.apply {
        if (expandableTimeBoardEL.isExpanded) {
            expandableTimeBoardEL.collapse()
            expandableSettingEL.expand()
        } else {
            expandableTimeBoardEL.expand()
            expandableSettingEL.collapse()
        }
    }
}