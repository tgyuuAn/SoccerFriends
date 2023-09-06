package com.tgyuu.presentation.feature.formation.memberlist.recyclerview

import android.content.ClipData
import android.content.ClipDescription
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.View.DragShadowBuilder
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tgyuu.domain.entity.Member
import com.tgyuu.presentation.databinding.ItemFormationTeamMemberBinding

class TeamViewHolder(val binding: ItemFormationTeamMemberBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(member: Member) {
        binding.member = member
        binding.root.setOnLongClickListener { v ->
            val item = ClipData.Item(member.id.toString())

            val dragData = ClipData(
                member.id.toString(),
                arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
                item
            )

            val shadow = MyDragShadowBuilder(binding.root)

            v.startDragAndDrop(
                dragData,
                shadow,
                null,
                0
            )

            true
        }
    }
}

private class MyDragShadowBuilder(view: View) : DragShadowBuilder(view) {

    private val shadow = ColorDrawable(Color.LTGRAY)

    override fun onProvideShadowMetrics(size: Point, touch: Point) {

        val width: Int = view.width / 2
        val height: Int = view.height / 2
        shadow.setBounds(0, 0, width, height)

        size.set(width, height)
        touch.set(width / 2, height / 2)
    }

    override fun onDrawShadow(canvas: Canvas) {
        shadow.draw(canvas)
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