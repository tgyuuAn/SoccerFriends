package com.tgyuu.presentation.feature.formation.memberlist.recyclerview

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tgyuu.presentation.common.base.dpToPx

class FormationTeamListDecoration(private val context: Context) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(5.dpToPx(), 0, 5.dpToPx(), 0)
    }
}
