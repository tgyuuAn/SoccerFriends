package com.tgyuu.presentation.feature.team.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tgyuu.domain.entity.Member
import com.tgyuu.presentation.databinding.ItemTeamMemberBinding

class TeamViewHolder(
    private val binding: ItemTeamMemberBinding,
    private val adapterViewModel: AdapterViewModel
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(member: Member) {
        binding.member = member
        binding.viewModel = adapterViewModel
    }
}

class TeamListAdapter(private val adapterViewModel: AdapterViewModel) :
    ListAdapter<Member, TeamViewHolder>(object : DiffUtil.ItemCallback<Member>() {
        override fun areItemsTheSame(oldItem: Member, newItem: Member): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Member, newItem: Member): Boolean {
            return oldItem == newItem
        }
    }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val binding =
            ItemTeamMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TeamViewHolder(binding,adapterViewModel)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}