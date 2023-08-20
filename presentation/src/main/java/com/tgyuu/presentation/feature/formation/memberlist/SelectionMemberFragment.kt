package com.tgyuu.presentation.feature.formation.memberlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tgyuu.presentation.common.base.BaseFragment
import com.tgyuu.presentation.databinding.FragmentSelectionMemberBinding
import com.tgyuu.presentation.feature.formation.FormationViewModel
import com.tgyuu.presentation.feature.formation.memberlist.recyclerview.FormationTeamListAdapter
import com.tgyuu.presentation.feature.formation.memberlist.recyclerview.FormationTeamListDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectionMemberFragment :
    BaseFragment<FragmentSelectionMemberBinding, FormationViewModel>(FragmentSelectionMemberBinding::inflate) {

    override val fragmentViewModel: FormationViewModel by viewModels(ownerProducer = { requireParentFragment() })
    private val formationTeamListAdapter: FormationTeamListAdapter by lazy { FormationTeamListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = fragmentViewModel.apply {
        }
        setRecyclerView()
    }

    private fun setRecyclerView() = binding.apply {
        selectionMemberListRV.apply {
            adapter = formationTeamListAdapter
            layoutManager = LinearLayoutManager(requireActivity())
            addItemDecoration(FormationTeamListDecoration(requireContext()))
        }
    }
}