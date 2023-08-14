package com.tgyuu.soccerfriends

import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.tgyuu.soccerfriends.common.base.BaseActivity
import com.tgyuu.soccerfriends.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>({ ActivityMainBinding.inflate(it) }) {
    override fun setSplash(){
        installSplashScreen()
    }
}