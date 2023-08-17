package com.tgyuu.soccerfriends.feature.formation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.tgyuu.soccerfriends.R
import com.tgyuu.soccerfriends.common.base.BaseFragment
import com.tgyuu.soccerfriends.common.base.repeatOnStarted
import com.tgyuu.soccerfriends.databinding.FragmentFormationBinding
import com.tgyuu.soccerfriends.feature.formation.viewpager.MemberAdapter

class FormationFragment :
    BaseFragment<FragmentFormationBinding, FormationViewModel>(FragmentFormationBinding::inflate) {
    override val fragmentViewModel: FormationViewModel by viewModels()
    private val viewPagerAdapter: MemberAdapter by lazy { MemberAdapter(this@FormationFragment) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setStatusBarAndIconColor(R.color.main, StatusBarIconColor.WHITE)

        binding.viewModel = fragmentViewModel.apply {
        }

        setViewPager2()
    }

    private fun setViewPager2() = binding.apply {
        val tabTextList =
            listOf(getString(R.string.selectionPlayer), getString(R.string.reservePlayer))

        memberListVP.adapter = viewPagerAdapter
        TabLayoutMediator(memberTL, memberListVP) { tab, pos ->
            tab.text = tabTextList[pos]
        }.attach()
    }
}