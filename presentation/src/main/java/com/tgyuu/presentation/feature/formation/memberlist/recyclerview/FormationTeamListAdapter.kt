package com.tgyuu.presentation.feature.formation.memberlist.recyclerview

import android.content.ClipData
import android.content.ClipDescription
import android.view.LayoutInflater
import android.view.View.DragShadowBuilder
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tgyuu.domain.entity.Member
import com.tgyuu.presentation.databinding.ItemFormationTeamMemberBinding

class TeamViewHolder(private val binding: ItemFormationTeamMemberBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(member: Member) {
        binding.member = member
        binding.root.setOnLongClickListener { view ->
            val item = ClipData.Item(member.id.toString())

            val dragData = ClipData(
                member.id.toString(),
                arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
                item
            )

            val shadow = DragShadowBuilder(binding.dragableItemCL)

            view.startDragAndDrop(
                dragData,
                shadow,
                null,
                0
            )

            true
        }
    }
}

class FormationTeamListAdapter :
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
            ItemFormationTeamMemberBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return TeamViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}