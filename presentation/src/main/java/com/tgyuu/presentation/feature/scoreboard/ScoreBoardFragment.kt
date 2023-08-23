package com.tgyuu.presentation.feature.scoreboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.tgyuu.domain.entity.Team
import com.tgyuu.presentation.R
import com.tgyuu.presentation.common.base.BaseFragment
import com.tgyuu.presentation.common.base.UiState
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
                    team.collect { handleTeamState(it) }
                }

                repeatOnStarted {
                    playTime.collect { handleTimeUI(it, TimeType.PLAY) }
                }

                repeatOnStarted {
                    alarmTime.collect { handleTimeUI(it, TimeType.ALARM) }
                }

                repeatOnStarted {
                    homeTeamScore.collect { handleScoreUI(it, ScoreType.HOME) }
                }

                repeatOnStarted {
                    awayTeamScore.collect { handleScoreUI(it, ScoreType.AWAY) }
                }
                getTeam()
            }
        }
    }

    private fun handleEvent(event: ScoreBoardViewModel.ScoreBoardEvent) {
        when (event) {
            ScoreBoardViewModel.ScoreBoardEvent.ClickButton -> handleExpandableLayout()
        }
    }

    private fun handleTeamState(teamState: UiState<Team>) {
        when (teamState) {
            UiState.Init -> {}

            UiState.Loading -> showLoadingScreen()

            is UiState.Success -> {
                hideLoadingScreen()
                updateTeam(teamState.data)
            }

            is UiState.Error -> {
                hideLoadingScreen()
                toast("팀 정보 갱신에 실패하였습니다.")
            }
        }
    }

    private fun handleExpandableLayout() = binding.apply {
        if (expandableTimeBoardEL.isExpanded) {
            expandSettingCollapseTimeBoard()
            setScoreBTNInvisible()
        } else {
            expandTimeBoardCollapseSetting()
            setScoreBTNVisible()
        }
    }

    private fun expandSettingCollapseTimeBoard() = binding.apply {
        expandableTimeBoardEL.collapse()
        expandableSettingEL.expand()
    }

    private fun expandTimeBoardCollapseSetting() = binding.apply {
        expandableTimeBoardEL.expand()
        expandableSettingEL.collapse()
    }

    private fun setScoreBTNInvisible() = binding.apply {
        awayTeamScorePlusBTN.visibility = View.GONE
        homeTeamScorePlusBTN.visibility = View.GONE
        awayTeamScoreMinusBTN.visibility = View.GONE
        homeTeamScoreMinusBTN.visibility = View.GONE
    }

    private fun setScoreBTNVisible() = binding.apply {
        awayTeamScorePlusBTN.visibility = View.VISIBLE
        homeTeamScorePlusBTN.visibility = View.VISIBLE
        awayTeamScoreMinusBTN.visibility = View.VISIBLE
        homeTeamScoreMinusBTN.visibility = View.VISIBLE
    }

    private fun showLoadingScreen() = binding.apply {
        scoreBoardLoadingView.visibility = View.VISIBLE
        scoreBoardLTV.visibility = View.VISIBLE
    }

    private fun hideLoadingScreen() = binding.apply {
        scoreBoardLoadingView.visibility = View.GONE
        scoreBoardLTV.visibility = View.GONE
    }

    private fun updateTeam(team: Team) {
        binding.homeTeamTV.text = team.name

        if (team.image.isEmpty()) {
            binding.homeTeamIV.setImageResource(R.drawable.circle)
            return
        }

        Glide.with(requireContext())
            .load(team.image)
            .circleCrop()
            .into(binding.homeTeamIV)
    }

    private fun handleTimeUI(score: Int, type: TimeType) {
        when (type) {
            TimeType.PLAY -> handlePlayTimeUI(score)
            TimeType.ALARM -> handleAlarmTimeUI(score)
        }
    }

    private fun handleScoreUI(time: Int, type: ScoreType) {
        when (type) {
            ScoreType.HOME -> handleHomeScoreUI(time)
            ScoreType.AWAY -> handleAwayScoreUI(time)
        }
    }

    /**
     * PlayTime이 AlarmTime보다 작을 수 없습니다.
     */
    private fun handlePlayTimeUI(score: Int) = binding.apply {
        if (score <= 0) {
            playTimeMinusBTN.isEnabled = false
        } else {
            playTimeMinusBTN.isEnabled = true
        }

        if (score >= 99) {
            playTimePlusBTN.isEnabled = false
        } else {
            playTimePlusBTN.isEnabled = true
        }

        if (score == fragmentViewModel.alarmTime.value) {
            alarmPlusBTN.isEnabled = false
            playTimeMinusBTN.isEnabled = false
        } else {
            alarmPlusBTN.isEnabled = true
            playTimeMinusBTN.isEnabled = true
        }

        if (score != fragmentViewModel.alarmTime.value) {
            playTimeMinusBTN.isEnabled = true
            playTimePlusBTN.isEnabled = true
        }
    }

    /**
     * AlarmTime이 Playime보다 클 수 없습니다.
     */
    private fun handleAlarmTimeUI(score: Int) = binding.apply {
        if (score <= 0) {
            alarmMinusBTN.isEnabled = false
        } else {
            alarmMinusBTN.isEnabled = true
        }

        if (score >= 99) {
            alarmPlusBTN.isEnabled = false
        } else {
            alarmPlusBTN.isEnabled = true
        }

        if (score == fragmentViewModel.playTime.value) {
            alarmPlusBTN.isEnabled = false
            playTimeMinusBTN.isEnabled = false
        } else {
            alarmPlusBTN.isEnabled = true
            playTimeMinusBTN.isEnabled = true
        }

        if (score != fragmentViewModel.playTime.value) {
            alarmMinusBTN.isEnabled = true
            alarmPlusBTN.isEnabled = true
        }
    }

    private fun handleHomeScoreUI(time: Int) = binding.apply {
        if (time <= 0) {
            playTimeMinusBTN.isEnabled = false
            return@apply
        }

        if (time >= 99) {
            playTimePlusBTN.isEnabled = false
            return@apply
        }
        playTimeMinusBTN.isEnabled = true
        playTimePlusBTN.isEnabled = true
    }

    private fun handleAwayScoreUI(time: Int) = binding.apply {
        if (time <= 0) {
            alarmMinusBTN.isEnabled = false
            return@apply
        }
        if (time >= 99) {
            alarmPlusBTN.isEnabled = false
            return@apply
        }
        alarmMinusBTN.isEnabled = true
        alarmPlusBTN.isEnabled = true
    }

}