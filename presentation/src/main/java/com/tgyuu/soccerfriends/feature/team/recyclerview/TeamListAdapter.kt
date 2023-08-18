package com.tgyuu.soccerfriends.feature.team.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tgyuu.domain.team.entity.Member
import com.tgyuu.soccerfriends.databinding.ItemTeamMemberBinding

class TeamViewHolder(val binding: ItemTeamMemberBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(member: Member) {
        binding.member = member
    }
}

class TeamListAdapter :
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
        return TeamViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}