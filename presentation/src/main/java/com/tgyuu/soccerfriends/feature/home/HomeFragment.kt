package com.tgyuu.soccerfriends.feature.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.tgyuu.soccerfriends.R
import com.tgyuu.soccerfriends.common.base.BaseFragment
import com.tgyuu.soccerfriends.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding,HomeViewModel>(FragmentHomeBinding::inflate) {
    override val fragmentViewModel: HomeViewModel by viewModels()
    
}