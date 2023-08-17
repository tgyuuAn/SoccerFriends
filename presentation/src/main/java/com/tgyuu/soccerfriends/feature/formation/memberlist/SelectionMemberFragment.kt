package com.tgyuu.soccerfriends.feature.formation.memberlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.tgyuu.soccerfriends.R
import com.tgyuu.soccerfriends.databinding.FragmentSelectionMemberBinding
import com.tgyuu.soccerfriends.feature.formation.FormationViewModel

class SelectionMemberFragment : Fragment() {

    private val fragmentViewModel: FormationViewModel by viewModels(ownerProducer = { requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSelectionMemberBinding.inflate(inflater, container, false)
        return binding.root
    }
}