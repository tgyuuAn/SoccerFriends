package com.tgyuu.soccerfriends.common.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

typealias Inflate<B> = (LayoutInflater, ViewGroup?, Boolean) -> B

abstract class BaseFragment<B : ViewDataBinding>(private val inflate: Inflate<B>) : Fragment() {

    protected var _binding: B? = null
    private val binding: B
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    fun log(str: String) = Log.d("tgyuu", str)

    fun toast(str: String) = Toast.makeText(requireContext(), str, Toast.LENGTH_SHORT).show()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}