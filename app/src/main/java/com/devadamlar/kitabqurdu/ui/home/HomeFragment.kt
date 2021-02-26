package com.devadamlar.kitabqurdu.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devadamlar.kitabqurdu.ActivityViewModel
import com.devadamlar.kitabqurdu.App
import com.devadamlar.kitabqurdu.MainActivity
import com.devadamlar.kitabqurdu.R
import com.devadamlar.kitabqurdu.models.Book
import com.google.android.material.snackbar.Snackbar
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class HomeFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val homeViewModel: HomeViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var activityViewModel: ActivityViewModel

    override fun onAttach(context: Context) {
        (requireActivity().applicationContext as App).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        activityViewModel = ViewModelProvider(requireActivity()).get(ActivityViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val searchText: TextView = root.findViewById(R.id.searchText)
        val searchButton: ImageButton = root.findViewById(R.id.searchButton)
        val recyclerView: RecyclerView = root.findViewById(R.id.recyclerView)
        val retryButton: Button = root.findViewById(R.id.retryButton)
        val progressBar: ProgressBar = root.findViewById(R.id.progressBar)
        val emptyView: TextView = root.findViewById(R.id.emptyView)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = BooksAdapter(requireContext(), onBookClicked)
        recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
            BooksLoadStateAdapter { adapter.retry() },
            BooksLoadStateAdapter { adapter.retry() }
        )
        homeViewModel.result
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe { adapter.submitData(lifecycle, it) }
        adapter.addLoadStateListener {
            recyclerView.isVisible = it.source.refresh is LoadState.NotLoading && adapter.itemCount != 0
            progressBar.isVisible = it.source.refresh is LoadState.Loading
            retryButton.isVisible = it.source.refresh is LoadState.Error
            emptyView.isVisible = it.source.refresh is LoadState.NotLoading
                    && adapter.itemCount == 0
                    && !homeViewModel.currentKeyword.isNullOrEmpty()

            val errorState = it.source.append as? LoadState.Error
                ?: it.source.prepend as? LoadState.Error
                ?: it.append as? LoadState.Error
                ?: it.prepend as? LoadState.Error
                ?: it.source.refresh as? LoadState.Error

            errorState?.error?.localizedMessage?.let { message ->
                Snackbar.make(root, message, Snackbar.LENGTH_LONG).show()
            }
        }

        retryButton.setOnClickListener { adapter.retry() }

        searchButton.setOnClickListener {
            if (searchText.text.toString().isBlank()) return@setOnClickListener
            (activity as MainActivity).hideKeyboard()
            homeViewModel.search(searchText.text.trim().toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { adapter.submitData(lifecycle, it) }
        }
        return root
    }

    private fun goToBook() {
        findNavController().navigate(R.id.bookFragment)
    }

    private val onBookClicked: (Book)-> Unit = {
        Log.d("Home", "Selected book: ${it.key}")
        activityViewModel.selectedBook.onNext(it)
        goToBook()
    }
}