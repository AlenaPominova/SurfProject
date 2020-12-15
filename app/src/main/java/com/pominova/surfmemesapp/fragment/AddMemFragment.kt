package com.pominova.surfmemesapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.pominova.surfmemesapp.R
import com.zharkovsky.memes.viewModels.AddViewModel

class AddMemFragment : Fragment() {
    private lateinit var addViewModel: AddViewModel
    private lateinit var textView: TextView
    private lateinit var root: View

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        addViewModel = ViewModelProvider(this).get(AddViewModel::class.java)
        root = inflater.inflate(R.layout.add_meme_fragment, container, false)
        textView = root.findViewById(R.id.add_meme_tv)
        addViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}