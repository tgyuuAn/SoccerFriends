package com.tgyuu.presentation.feature.home

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import com.tgyuu.presentation.R
import com.tgyuu.presentation.common.base.BaseFragment
import com.tgyuu.presentation.common.base.repeatOnStarted
import com.tgyuu.presentation.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import java.security.Permission

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

        getReadMediaPermission()
    }

    fun getReadMediaPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkPermission(Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private fun checkPermission(permission: String) {
        galleryPermissionLauncher.launch(permission)
    }

    private val galleryPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {

            } else {
                toast("갤러리 접근 권한이 반드시 있어야 합니다.")
                requireActivity().finish()
            }
        }

    private fun handleEvent(event: HomeViewModel.HomeEvent) {
        when (event) {
            HomeViewModel.HomeEvent.MoveToScoreBoard -> navigateWithUriNavOptions(
                "soccerfriends://score_board_nav",
                NavOptions.Builder().setPopUpTo(R.id.homeFragment, false).build()
            )

            HomeViewModel.HomeEvent.MoveToTeamManagement -> navigateWithUriNavOptions(
                "soccerfriends://team_nav",
                NavOptions.Builder().setPopUpTo(R.id.homeFragment, false).build()
            )

            HomeViewModel.HomeEvent.MoveToFormation -> navigateWithUriNavOptions(
                "soccerfriends://formation_nav",
                NavOptions.Builder().setPopUpTo(R.id.homeFragment, false).build()
            )
        }
    }
}