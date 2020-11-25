package com.pominova.surfmemesapp.util

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.pominova.surfmemesapp.R

class ProgressButtonUtil internal constructor(view: View, btnText: String?) {
    private val progressBar: ProgressBar = view.findViewById(R.id.progress_bar)
    private val textView: TextView = view.findViewById(R.id.progress_btn_tv)

    private var text: String? = btnText

    init {
        textView.text = text
    }

    fun activateButton() {
        progressBar.visibility = View.VISIBLE
        textView.text = ""
    }

    fun finishButton() {
        progressBar.visibility = View.GONE
        textView.text = text
    }
}
