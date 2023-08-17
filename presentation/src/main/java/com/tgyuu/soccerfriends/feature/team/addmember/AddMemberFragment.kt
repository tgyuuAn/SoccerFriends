package com.tgyuu.soccerfriends.feature.team.addmember

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.tgyuu.soccerfriends.R
import com.tgyuu.soccerfriends.common.base.BaseFragment
import com.tgyuu.soccerfriends.databinding.FragmentAddMemberBinding


class AddMemberFragment :
    BaseFragment<FragmentAddMemberBinding, AddMemberViewModel>(FragmentAddMemberBinding::inflate) {
    override val fragmentViewModel: AddMemberViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = fragmentViewModel.apply {

        }
    }
}