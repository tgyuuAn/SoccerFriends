package com.tgyuu.soccerfriends.feature.formation.memberlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tgyuu.soccerfriends.R
import com.tgyuu.soccerfriends.common.base.BaseFragment
import com.tgyuu.soccerfriends.databinding.FragmentReserveMemberBinding
import com.tgyuu.soccerfriends.feature.formation.FormationViewModel
import com.tgyuu.soccerfriends.feature.formation.memberlist.recyclerview.FormationTeamListAdapter
import com.tgyuu.soccerfriends.feature.team.recyclerview.TeamListAdapter

class ReserveMemberFragment :
    BaseFragment<FragmentReserveMemberBinding, FormationViewModel>(FragmentReserveMemberBinding::inflate) {

    override val fragmentViewModel: FormationViewModel by viewModels(ownerProducer = { requireParentFragment() })
    private val formationTeamListAdapter: FormationTeamListAdapter by lazy { FormationTeamListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel=fragmentViewModel.apply{

        }
        setRecyclerView()
    }

    private fun setRecyclerView() = binding.apply{
        reserveMemberListRV.apply{
            adapter = formationTeamListAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }
    }
}