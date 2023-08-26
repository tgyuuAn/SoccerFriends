package com.tgyuu.presentation.feature.scoreboard

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlarmVibrator @Inject constructor(@ApplicationContext context: Context) {

    private val vibrator: Vibrator by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                context?.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
    }

    fun vibrate(time: Long) {
        vibrator.vibrate(
            VibrationEffect.createOneShot(
                time,
                VibrationEffect.DEFAULT_AMPLITUDE,
            )
        )
    }
}