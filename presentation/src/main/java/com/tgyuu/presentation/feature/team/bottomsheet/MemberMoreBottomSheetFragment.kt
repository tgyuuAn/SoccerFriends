package com.tgyuu.presentation.feature.team.bottomsheet

import android.app.Dialog
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tgyuu.presentation.R
import com.tgyuu.presentation.common.base.repeatOnStarted
import com.tgyuu.presentation.databinding.FragmentMemberMoreBottomSheetBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MemberMoreBottomSheetFragment(private val callBack: () -> Unit) :
    BottomSheetDialogFragment() {

    private var _binding: FragmentMemberMoreBottomSheetBinding? = null
    val binding get() = _binding!!
    private val fragmentViewModel: MemberMoreBottomSheetViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMemberMoreBottomSheetBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetDialog.setOnShowListener { onShowListner(bottomSheetDialog) }
        return bottomSheetDialog
    }

    private fun onShowListner(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet = bottomSheetDialog
            .findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)

        if (bottomSheet != null) {
            blockBottomSheetDragging(bottomSheet)
        }
    }

    private fun blockBottomSheetDragging(bottomSheet: FrameLayout) {
        val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(bottomSheet)
        behavior.isDraggable = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBinding()
    }

    private fun setBinding() {
        binding.apply {
            viewModel = fragmentViewModel.apply {
                viewLifecycleOwner.apply {
                    repeatOnStarted { }
                }
            }
        }
    }

    private fun highlight(view: TextView) {
        val flagTextViewList = listOf(
            binding.changeNickNameTV,
            binding.removeImageTV,
            binding.kickOutPlayerTV,
            binding.changeImageTV
        )

        flagTextViewList.forEach { flagTextView ->
            if (view == flagTextView) {
                highlightTextView(view)
            } else {
                normalTextView(flagTextView)
            }
        }
    }

    private fun highlightTextView(view: TextView) {
        view.apply {
            setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.main,
                ),
            )
            setTypeface(null, Typeface.BOLD)
        }
    }

    private fun normalTextView(view: TextView) {
        view.apply {
            setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.gray3,
                ),
            )
            setTypeface(null, Typeface.NORMAL)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        callBack()
    }
}