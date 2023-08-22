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

    enum class TimeType {
        PLAY, ALARM
    }

    enum class ScoreType {
        HOME, AWAY
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setStatusBarAndIconColor(R.color.main, StatusBarIconColor.WHITE)

        binding.apply {
            viewModel = fragmentViewModel.apply {
                repeatOnStarted {
                    eventFlow.collect { handleEvent(it) }
                }
                repeatOnStarted {
                    playTime.collect { handleTimeUI(it, TimeType.PLAY) }
                }
                repeatOnStarted {
                    homeTeamScore.collect { handleScoreUI(it, ScoreType.HOME) }
                }
                repeatOnStarted {
                    awayTeamScore.collect { handleScoreUI(it, ScoreType.AWAY) }
                }
            }
        }
    }

    private fun handleEvent(event: ScoreBoardViewModel.ScoreBoardEvent) {
        when (event) {
            ScoreBoardViewModel.ScoreBoardEvent.ClickButton -> handleExpandableLayout()
        }
    }

    private fun handleExpandableLayout() = binding.apply {
        if (expandableTimeBoardEL.isExpanded) {
            expandableTimeBoardEL.collapse()
            expandableSettingEL.expand()
            awayTeamScorePlusBTN.visibility = View.GONE
            homeTeamScorePlusBTN.visibility = View.GONE
        } else {
            expandableTimeBoardEL.expand()
            expandableSettingEL.collapse()
            awayTeamScorePlusBTN.visibility = View.VISIBLE
            homeTeamScorePlusBTN.visibility = View.VISIBLE
        }
    }

    private fun handleTimeUI(score: Int, type: TimeType) {
        when (type) {
            TimeType.PLAY -> {
                if (score <= 0) {
                    binding.playTimeMinusBTN.isEnabled = false
                    return
                }

                if (score >= 99) {
                    binding.playTimePlusBTN.isEnabled = false
                    return
                }
                binding.playTimeMinusBTN.isEnabled = true
                binding.playTimePlusBTN.isEnabled = true
            }

            TimeType.ALARM -> {
                if (score <= 0) {
                    binding.alarmMinusBTN.isEnabled = false
                    return
                }
                if (score >= 99) {
                    binding.alarmPlusBTN.isEnabled = false
                    return
                }
                binding.alarmMinusBTN.isEnabled = true
                binding.alarmPlusBTN.isEnabled = true
            }
        }
    }

    private fun handleScoreUI(time: Int, type: ScoreType) {
        when (type) {
            ScoreType.HOME -> {
                if (time <= 0) {
                    binding.playTimeMinusBTN.isEnabled = false
                    return
                }

                if (time >= 99) {
                    binding.playTimePlusBTN.isEnabled = false
                    return
                }
                binding.playTimeMinusBTN.isEnabled = true
                binding.playTimePlusBTN.isEnabled = true
            }

            ScoreType.AWAY -> {
                if (time <= 0) {
                    binding.alarmMinusBTN.isEnabled = false
                    return
                }
                if (time >= 99) {
                    binding.alarmPlusBTN.isEnabled = false
                    return
                }
                binding.alarmMinusBTN.isEnabled = true
                binding.alarmPlusBTN.isEnabled = true
            }
        }
    }
}