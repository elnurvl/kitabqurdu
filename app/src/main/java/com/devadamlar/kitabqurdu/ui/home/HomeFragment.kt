package com.devadamlar.kitabqurdu.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.devadamlar.kitabqurdu.App
import com.devadamlar.kitabqurdu.R
import com.devadamlar.kitabqurdu.viewmodel.ViewModelFactory
import javax.inject.Inject

class HomeFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val homeViewModel: HomeViewModel by viewModels {
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        (requireActivity().applicationContext as App).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        return root
    }
}