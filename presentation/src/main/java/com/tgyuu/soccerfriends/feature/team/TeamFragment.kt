package com.tgyuu.soccerfriends.feature.team

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.tgyuu.soccerfriends.common.base.BaseFragment
import com.tgyuu.soccerfriends.databinding.FragmentTeamBinding

class TeamFragment :
    BaseFragment<FragmentTeamBinding, TeamViewModel>(FragmentTeamBinding::inflate) {
    override val fragmentViewModel: TeamViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel = fragmentViewModel.apply {

            }
        }
    }
}