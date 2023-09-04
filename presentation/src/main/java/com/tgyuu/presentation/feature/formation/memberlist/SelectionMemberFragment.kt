package com.tgyuu.presentation.feature.formation.memberlist

import android.content.ClipData
import android.content.ClipDescription
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tgyuu.domain.entity.Member
import com.tgyuu.presentation.common.base.BaseFragment
import com.tgyuu.presentation.common.base.UiState
import com.tgyuu.presentation.common.base.repeatOnStarted
import com.tgyuu.presentation.databinding.FragmentSelectionMemberBinding
import com.tgyuu.presentation.feature.formation.FormationViewModel
import com.tgyuu.presentation.feature.formation.memberlist.recyclerview.FormationTeamListAdapter
import com.tgyuu.presentation.feature.formation.memberlist.recyclerview.FormationTeamListDecoration
import com.tgyuu.presentation.feature.formation.memberlist.recyclerview.FormationTeamListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectionMemberFragment :
    BaseFragment<FragmentSelectionMemberBinding, FormationViewModel>(FragmentSelectionMemberBinding::inflate) {

    override val fragmentViewModel: FormationViewModel by viewModels(ownerProducer = { requireParentFragment() })
    private val adapterViewModel: FormationTeamListViewModel by viewModels(ownerProducer = { requireParentFragment() })
    private val formationTeamListAdapter: FormationTeamListAdapter by lazy { FormationTeamListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = fragmentViewModel.apply {
            repeatOnStarted {
                selectionMemberList.collect { handleSelectionMemberListState(it) }
            }
            getSelectionMemberList()
        }
        adapterViewModel.apply {
            repeatOnStarted {
                eventFlow.collect { handleAdapterEvent(it) }
            }
        }
        setRecyclerView()
    }

    private fun handleAdapterEvent(event: FormationTeamListViewModel.FormationTeamListEvent) {
        when (event) {
            is FormationTeamListViewModel.FormationTeamListEvent.DragStart -> {
                val item = ClipData.Item(event.id.toString() as CharSequence)

                val dragData = ClipData(
                    event.id.toString() as CharSequence,
                    arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
                    item
                )
            }
        }
    }

    private fun handleSelectionMemberListState(uiState: UiState<List<Member>>) {
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
        selectionMemberListRV.apply {
            adapter = formationTeamListAdapter
            addItemDecoration(FormationTeamListDecoration(requireContext()))
        }
    }
}