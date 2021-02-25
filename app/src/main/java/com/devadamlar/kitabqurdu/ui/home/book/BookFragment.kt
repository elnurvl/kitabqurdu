package com.devadamlar.kitabqurdu.ui.home.book

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.devadamlar.kitabqurdu.ActivityViewModel
import com.devadamlar.kitabqurdu.App
import com.devadamlar.kitabqurdu.R
import com.squareup.picasso.Picasso
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class BookFragment : Fragment() {
    private lateinit var thumbnail: ImageView
    private lateinit var titleText: TextView

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: BookViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var activityViewModel: ActivityViewModel

    override fun onAttach(context: Context) {
        (requireActivity().applicationContext as App).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.book_fragment, container, false)
        titleText = root.findViewById(R.id.titleText)
        thumbnail = root.findViewById(R.id.imageView)
        thumbnail.setImageResource(R.drawable.sample_cover)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activityViewModel = ViewModelProvider(requireActivity()).get(ActivityViewModel::class.java)
        activityViewModel.selectedBook.observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("Book", it.toString())
                titleText.text = it?.title
                Picasso.get()
                    .load(getString(R.string.cover_api_url) + it.coverI + "-M.jpg")
                    .placeholder(R.drawable.progress_animation)
                    .error(R.drawable.sample_cover)
                    .into(thumbnail)
            }, {
                Log.d("Book", it.message ?: "")
            })
    }
}