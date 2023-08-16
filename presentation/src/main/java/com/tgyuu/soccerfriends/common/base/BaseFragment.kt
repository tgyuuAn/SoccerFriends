package com.tgyuu.soccerfriends.common.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<T : ViewDataBinding, V : ViewModel>(private val inflate: Inflate<T>) :
    Fragment() {

    enum class StatusBarIconColor(val value: Boolean) {
        WHITE(false), BLACK(true)
    }

    private var _binding: T? = null
    val binding get() = _binding!!

    protected abstract val fragmentViewModel: V

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setStatusBarAndIconColor(@ColorRes barColor: Int, iconColor: StatusBarIconColor) {
        requireActivity().window.apply {
            statusBarColor = ContextCompat.getColor(requireContext(), barColor)
            WindowInsetsControllerCompat(this, this.decorView).isAppearanceLightStatusBars =
                iconColor.value
        }
    }

    fun log(str: String) = Log.d("wap", str)

    fun toast(str: String) = Toast.makeText(requireContext(), str, Toast.LENGTH_SHORT).show()
}