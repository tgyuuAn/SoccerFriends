package com.tgyuu.presentation.feature.formation.memberlist

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tgyuu.domain.entity.Member
import com.tgyuu.presentation.common.base.BaseFragment
import com.tgyuu.presentation.common.base.UiState
import com.tgyuu.presentation.common.base.repeatOnStarted
import com.tgyuu.presentation.databinding.FragmentReserveMemberBinding
import com.tgyuu.presentation.feature.formation.FormationViewModel
import com.tgyuu.presentation.feature.formation.memberlist.recyclerview.FormationTeamListAdapter
import com.tgyuu.presentation.feature.formation.memberlist.recyclerview.FormationTeamListDecoration
import com.tgyuu.presentation.feature.formation.memberlist.recyclerview.FormationTeamListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReserveMemberFragment :
    BaseFragment<FragmentReserveMemberBinding, FormationViewModel>(FragmentReserveMemberBinding::inflate) {

    override val fragmentViewModel: FormationViewModel by viewModels(ownerProducer = { requireParentFragment() })
    private val adapterViewModel: FormationTeamListViewModel by viewModels(ownerProducer = { requireParentFragment() })
    private val formationTeamListAdapter: FormationTeamListAdapter by lazy { FormationTeamListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = fragmentViewModel.apply {
            repeatOnStarted { reserveMemberList.collect { handleReserveMemberListState(it) } }
            getReserveMemberList()
        }
        setRecyclerView()
    }

    private fun handleReserveMemberListState(uiState: UiState<List<Member>>) {
        when (uiState) {
            UiState.Init -> {}
            UiState.Loading -> {}
            is UiState.Success -> {
                formationTeamListAdapter.submitList(uiState.data)
            }

            is UiState.Error -> {
                toast(uiState.message)
            }
        }
    }

    private fun setRecyclerView() = binding.apply {
        reserveMemberListRV.apply {
            adapter = formationTeamListAdapter
            addItemDecoration(FormationTeamListDecoration(requireContext()))
        }
    }
}