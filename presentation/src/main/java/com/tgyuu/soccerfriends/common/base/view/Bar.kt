package com.tgyuu.soccerfriends.common.base.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import com.tgyuu.soccerfriends.R

class Bar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    lateinit var title: TextView
    lateinit var backButton : ImageView

    init {
        val infService = Context.LAYOUT_INFLATER_SERVICE
        val inflater = context.getSystemService(infService) as LayoutInflater
        val view = inflater.inflate(R.layout.bar, this, false)
        addView(view)

        title = findViewById<TextView>(R.id.barTitleTV)
        backButton = findViewById<ImageView>(R.id.barBackIV)

        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.Bar)

        val titleText = typedArray.getString(R.styleable.Bar_title)
        title.text = titleText

        backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        typedArray.recycle()
    }
}