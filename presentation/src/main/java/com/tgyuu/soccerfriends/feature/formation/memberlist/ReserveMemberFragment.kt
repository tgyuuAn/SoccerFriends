package com.tgyuu.soccerfriends.feature.formation.memberlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tgyuu.soccerfriends.R
import com.tgyuu.soccerfriends.databinding.FragmentReserveMemberBinding

class ReserveMemberFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentReserveMemberBinding.inflate(inflater,container,false)
        return binding.root
    }
}