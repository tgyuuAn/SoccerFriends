package com.tgyuu.presentation.feature.scoreboard

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.tgyuu.domain.entity.Team
import com.tgyuu.presentation.R
import com.tgyuu.presentation.common.base.BaseFragment
import com.tgyuu.presentation.common.base.UiState
import com.tgyuu.presentation.common.base.repeatOnStarted
import com.tgyuu.presentation.databinding.FragmentScoreBoardBinding
import dagger.hilt.android.AndroidEntryPoint
import net.cachapa.expandablelayout.ExpandableLayout
import java.time.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class ScoreBoardFragment :
    BaseFragment<FragmentScoreBoardBinding, ScoreBoardViewModel>(FragmentScoreBoardBinding::inflate) {
    override val fragmentViewModel: ScoreBoardViewModel by viewModels()
    private val alarmVibrator: AlarmVibrator by lazy { AlarmVibrator(requireContext()) }

    enum class TimeType {
        PLAY, ALARM
    }

    enum class ScoreType {
        HOME, AWAY
    }

    companion object {
        const val MAX_VALUE = 99
        const val MIN_VALUE = 0
        const val LONG_TO_SECOND = 1000L

        /**
         * 토탈 시간을 십의 자리 시간으로 바꿔줍니다.
         *
         * ex)
         *
         * 59분 -> 50분
         *
         * 59초 -> 50초
         */
        fun Long.totalToTens() = (this / 10)

        /**
         * 토탈 시간을 일의 자리 시간으로 바꿔줍니다.
         *
         * ex)
         *
         * 59분 -> 9분
         *
         * 59초 -> 9초
         */
        fun Long.tensToDigit() = (this % 10)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setStatusBarAndIconColor(R.color.main, StatusBarIconColor.WHITE)
        setDateTime()

        binding.apply {
            viewModel = fragmentViewModel.apply {
                repeatOnStarted { eventFlow.collect { handleEvent(it) } }
                repeatOnStarted { gameState.collect { changeGameState(it) } }
                repeatOnStarted { team.collect { handleTeamState(it) } }
                repeatOnStarted { playTime.collect { handleTimeUI(it, TimeType.PLAY) } }
                repeatOnStarted { alarmTime.collect { handleTimeUI(it, TimeType.ALARM) } }
                repeatOnStarted { homeTeamScore.collect { handleScoreUI(it, ScoreType.HOME) } }
                repeatOnStarted { awayTeamScore.collect { handleScoreUI(it, ScoreType.AWAY) } }
                repeatOnStarted { timer.collect { setTimerUI(it) } }
                getTeam()
            }
        }
    }

    private fun setDateTime() {
        val currentDateTime = LocalDate.now()
        val month = currentDateTime.month.toString().substring(0 until 3)
        var day = currentDateTime.dayOfMonth.toString()
        if (day.length == 1) day = "0" + day

        binding.calendarTV.text = "${day} ${month}"
    }

    private fun handleEvent(event: ScoreBoardViewModel.ScoreBoardEvent) {
        when (event) {
            ScoreBoardViewModel.ScoreBoardEvent.ClickPause -> setPauseButtonText()
            ScoreBoardViewModel.ScoreBoardEvent.ChangeAwayTeamImage -> navigateToGallery()
            is ScoreBoardViewModel.ScoreBoardEvent.Error -> toast(event.message)
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

    private fun changeGameState(gameState: ScoreBoardViewModel.GameState) {
        when (gameState) {
            ScoreBoardViewModel.GameState.GameNotStarted -> initGameState()
            ScoreBoardViewModel.GameState.GameInProgress -> gameStart()
            is ScoreBoardViewModel.GameState.GameResult -> gameResult(
                gameState.score.first,
                gameState.score.second
            )
        }
    }

    private fun initGameState() {
        fragmentViewModel.resetAllValue()
        setGameNotStartedUI()
    }

    private fun gameStart() {
        setGameInProgressUI()
        fragmentViewModel.startTimer()
    }

    private fun gameResult(homeScore: Int, awayScore: Int) {
        setGameResultUI(homeScore, awayScore)
        alarmVibrator.vibrate(LONG_TO_SECOND)
    }

    private fun setGameNotStartedUI() = binding.apply {
        handleScoreBTNVisible(View.GONE)
        expand(expandableSettingEL)
    }

    private fun setGameInProgressUI() = binding.apply {
        handleScoreBTNVisible(View.VISIBLE)
        scoreBoardBTN.text = getString(R.string.matchSet)
        expand(expandableTimeBoardEL)
    }

    private fun setGameResultUI(homeScore: Int, awayScore: Int) = binding.apply {
        handleScoreBTNVisible(View.GONE)
        scoreBoardBTN.text = getString(R.string.end)
        expand(expandableGameResultEL)

        val homeTeamName = binding.homeTeamTV.text.toString()
        val awayTeamName = binding.awayTeamTV.text.toString()

        if (homeScore > awayScore) {
            gameResultDescriptionTV.text =
                getGameResultWinSpan(homeTeamName, awayTeamName, homeScore, awayScore)
        } else if (awayScore > homeScore) {
            gameResultDescriptionTV.text =
                getGameResultWinSpan(awayTeamName, homeTeamName, awayScore, homeScore)
        } else {
            gameResultDescriptionTV.text =
                getGameResultDrawSpan(awayTeamName, homeTeamName, awayScore, homeScore)
        }
    }

    private fun handleScoreBTNVisible(visibility: Int) = binding.apply {
        awayTeamScorePlusBTN.visibility = visibility
        homeTeamScorePlusBTN.visibility = visibility
        awayTeamScoreMinusBTN.visibility = visibility
        homeTeamScoreMinusBTN.visibility = visibility
        pauseBTN.visibility = visibility
    }

    private fun setPauseButtonText() = binding.apply {
        if (fragmentViewModel.timerJob == null) pauseBTN.text = getString(R.string.restartMatch)
        else pauseBTN.text = getString(R.string.pause)
    }

    private fun expand(expandableLayout: ExpandableLayout) {
        val expandableList = listOf(
            binding.expandableSettingEL,
            binding.expandableGameResultEL,
            binding.expandableTimeBoardEL
        )

        expandableList.forEach {
            if (it == expandableLayout) {
                it.expand()
                it.visibility = View.VISIBLE
            } else {
                it.collapse()
                it.visibility = View.GONE
            }
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
            val imageUri = it.data!!.data.toString()

            setAwayTeamImage(imageUri)
        }
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
            .load(team.image.toUri())
            .circleCrop()
            .into(binding.homeTeamIV)
    }

    private fun handleTimeUI(time: Int, type: TimeType) {
        when (type) {
            TimeType.PLAY -> handlePlayTimeUI(time)
            TimeType.ALARM -> {
                handleAlarmTimeUI(time)
            }
        }
    }

    private fun handleScoreUI(time: Int, type: ScoreType) {
        when (type) {
            ScoreType.HOME -> handleHomeScoreUI(time)
            ScoreType.AWAY -> handleAwayScoreUI(time)
        }
    }

    private fun handlePlayTimeUI(time: Int) = binding.apply {
        if (time <= MIN_VALUE) {
            playTimeMinusBTN.isEnabled = false
        } else {
            playTimeMinusBTN.isEnabled = true
        }

        if (time >= MAX_VALUE) {
            playTimePlusBTN.isEnabled = false
        } else {
            playTimePlusBTN.isEnabled = true
        }

        if (time == fragmentViewModel.alarmTime.value) {
            alarmPlusBTN.isEnabled = false
            playTimeMinusBTN.isEnabled = false
        } else {
            alarmPlusBTN.isEnabled = true
            playTimeMinusBTN.isEnabled = true
        }

        if (time != fragmentViewModel.alarmTime.value) {
            playTimeMinusBTN.isEnabled = true
            playTimePlusBTN.isEnabled = true
        }
    }

    private fun handleAlarmTimeUI(score: Int) = binding.apply {
        if (score <= MIN_VALUE) {
            alarmMinusBTN.isEnabled = false
        } else {
            alarmMinusBTN.isEnabled = true
        }

        if (score >= MAX_VALUE) {
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
        if (time <= MIN_VALUE) {
            playTimeMinusBTN.isEnabled = false
            return@apply
        }

        if (time >= MAX_VALUE) {
            playTimePlusBTN.isEnabled = false
            return@apply
        }
        playTimeMinusBTN.isEnabled = true
        playTimePlusBTN.isEnabled = true
    }

    private fun handleAwayScoreUI(time: Int) = binding.apply {
        if (time <= MIN_VALUE) {
            alarmMinusBTN.isEnabled = false
            return@apply
        }
        if (time >= MAX_VALUE) {
            alarmPlusBTN.isEnabled = false
            return@apply
        }
        alarmMinusBTN.isEnabled = true
        alarmPlusBTN.isEnabled = true
    }

    private fun setTimerUI(timeMillis: Long) {
        val minute = (timeMillis / (60 * LONG_TO_SECOND))
        val second = (timeMillis % (60 * LONG_TO_SECOND)) / LONG_TO_SECOND

        if (fragmentViewModel.gameState.value == ScoreBoardViewModel.GameState.GameInProgress
            && timeMillis == fragmentViewModel.alarmTime.value * 60 * LONG_TO_SECOND
        ) {
            alarmVibrator.vibrate(LONG_TO_SECOND)
        }

        binding.apply {
            timerMinuteTV1.text = minute.totalToTens().toString()
            timerMinuteTV2.text = minute.tensToDigit().toString()
            timerSecondTV1.text = second.totalToTens().toString()
            timerSecondTV2.text = second.tensToDigit().toString()
        }
    }

    private fun setAwayTeamImage(imageUri: String) {
        if (imageUri.isEmpty()) {
            binding.awayTeamIV.setImageResource(R.drawable.circle)
            return
        }

        Glide.with(requireContext())
            .load(imageUri)
            .circleCrop()
            .into(binding.awayTeamIV)
    }

    private fun getGameResultWinSpan(
        winTeam: String,
        loseTeam: String,
        winScore: Int,
        loseScore: Int
    ): SpannableStringBuilder {
        val text = String.format(
            getString(
                R.string.gameResultWinDescription
            ),
            winTeam,
            loseTeam,
            winScore,
            loseScore
        )
        return SpannableStringBuilder(text).apply {
            setSpan(
                ForegroundColorSpan(Color.BLACK),
                0, // start
                winTeam.length, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
            setSpan(
                ForegroundColorSpan(Color.BLACK),
                winTeam.length + 4, // start
                winTeam.length + 4 + loseTeam.length, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
            setSpan(
                ForegroundColorSpan(Color.BLACK),
                winTeam.length + 4 + loseTeam.length + 4, // start
                winTeam.length + 4 + loseTeam.length + 4 + winScore.toString().length + loseScore.toString().length + 3, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
            setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(requireContext(), R.color.dark_green)
                ),
                text.indexOf("승리"), // start
                text.indexOf("승리") + 2, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
        }
    }

    private fun getGameResultDrawSpan(
        homeTeam: String,
        awayTeam: String,
        homeScore: Int,
        awayScore: Int
    ): SpannableStringBuilder {
        val text = String.format(
            getString(
                R.string.gameResultDrawDescription
            ),
            homeTeam,
            awayTeam,
            homeScore,
            awayScore
        )
        return SpannableStringBuilder(text).apply {
            setSpan(
                ForegroundColorSpan(Color.BLACK),
                0, // start
                homeTeam.length, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
            setSpan(
                ForegroundColorSpan(Color.BLACK),
                homeTeam.length + 4, // start
                homeTeam.length + 4 + awayTeam.length, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
            setSpan(
                ForegroundColorSpan(Color.BLACK),
                homeTeam.length + 4 + awayTeam.length + 4, // start
                homeTeam.length + 4 + awayTeam.length + 4 + homeScore.toString().length + awayScore.toString().length + 3, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
            setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(requireContext(), R.color.blue)
                ),
                text.indexOf("무승부"), // start
                text.indexOf("무승부") + 3, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
        }
    }
}