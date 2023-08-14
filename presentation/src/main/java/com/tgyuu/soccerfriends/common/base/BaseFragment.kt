package com.tgyuu.soccerfriends.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

typealias Inflate<B> = (LayoutInflater, ViewGroup?, Boolean) -> B

class BaseFragment<B : ViewDataBinding>(private val inflate : Inflate<B>) : Fragment() {

    protected var _binding : B? = null
    private val binding : B
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}