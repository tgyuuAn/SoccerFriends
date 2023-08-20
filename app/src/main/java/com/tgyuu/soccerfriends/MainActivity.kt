package com.tgyuu.soccerfriends

import androidx.navigation.fragment.NavHostFragment
import com.tgyuu.presentation.common.base.BaseActivity
import com.tgyuu.soccerfriends.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>({ ActivityMainBinding.inflate(it) }) {

    override fun afterBinding() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
    }
}