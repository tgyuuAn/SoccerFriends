package com.tgyuu.soccerfriends.feature.formation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.tgyuu.soccerfriends.R
import com.tgyuu.soccerfriends.common.base.BaseFragment
import com.tgyuu.soccerfriends.databinding.FragmentFormationBinding
import com.tgyuu.soccerfriends.feature.formation.memberlist.ReserveMemberFragment
import com.tgyuu.soccerfriends.feature.formation.memberlist.SelectionMemberFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FormationFragment :
    BaseFragment<FragmentFormationBinding, FormationViewModel>(FragmentFormationBinding::inflate) {

    override val fragmentViewModel: FormationViewModel by viewModels()

    private val selectionMemberFragment: SelectionMemberFragment by lazy { SelectionMemberFragment() }
    private val reserveMemberFragment: ReserveMemberFragment by lazy { ReserveMemberFragment() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setStatusBarAndIconColor(R.color.main, StatusBarIconColor.WHITE)

        binding.viewModel = fragmentViewModel.apply {

        }
        setTabLayout()
    }


    private fun setTabLayout() {
        initTabLayout()
        addTabSelectedListener()
    }

    private fun initTabLayout() {
        childFragmentManager.commit {
            add(binding.memberListFCV.id, selectionMemberFragment)
            add(binding.memberListFCV.id, reserveMemberFragment)
            hide(reserveMemberFragment)
        }
    }

    private fun addTabSelectedListener() = binding.apply {
        memberTL.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                log(tab?.position.toString())

                val selectedUnSelectedFragment = when (tab?.position) {
                    1 -> reserveMemberFragment to selectionMemberFragment
                    else -> selectionMemberFragment to reserveMemberFragment
                }

                childFragmentManager.commit {
                    if (selectedUnSelectedFragment.first.isHidden) show(selectedUnSelectedFragment.first)
                    if (!selectedUnSelectedFragment.second.isHidden) hide(selectedUnSelectedFragment.second)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }
}