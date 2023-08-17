package com.tgyuu.soccerfriends.feature.formation.viewpager

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tgyuu.soccerfriends.feature.formation.memberlist.ReserveMemberFragment
import com.tgyuu.soccerfriends.feature.formation.memberlist.SelectionMemberFragment

class MemberAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val fragments: List<Fragment> =
        listOf(SelectionMemberFragment(), ReserveMemberFragment())

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}