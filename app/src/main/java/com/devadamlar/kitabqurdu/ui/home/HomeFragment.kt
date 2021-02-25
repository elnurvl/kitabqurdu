package com.devadamlar.kitabqurdu.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devadamlar.kitabqurdu.ActivityViewModel
import com.devadamlar.kitabqurdu.App
import com.devadamlar.kitabqurdu.MainActivity
import com.devadamlar.kitabqurdu.R
import com.devadamlar.kitabqurdu.models.Book
import com.devadamlar.kitabqurdu.viewmodel.ViewModelFactory
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
        val progressBar: ProgressBar = root.findViewById(R.id.progressBar)
        val emptyText: TextView = root.findViewById(R.id.emptyText)

        val adapter = BooksAdapter(requireContext(), object : BooksAdapter.ItemClickListener {
            override fun onItemClicked(view: View, book: Book) {
                Log.d("Home", "Selected book: ${book.key}")
                activityViewModel.selectedBook.onNext(book)
                goToBook()
            }

        })
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        homeViewModel.books.observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                adapter.books = it ?: emptyList()
                adapter.notifyDataSetChanged()
            }

        searchButton.setOnClickListener {
            if (searchText.text.toString().isEmpty()) return@setOnClickListener
            (activity as MainActivity).hideKeyboard()
            emptyText.visibility = View.GONE
            adapter.books = emptyList()
            adapter.notifyDataSetChanged()
            progressBar.visibility = View.VISIBLE
            homeViewModel.search(searchText.text.toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result, error ->
                    progressBar.visibility = View.GONE
                    if (result?.docs.isNullOrEmpty() && error == null) emptyText.visibility = View.VISIBLE
                    homeViewModel.books.onNext(result?.docs?: emptyList())
                    Log.d("Home", error?.message?: "No error")
                    if (error != null) Snackbar.make(root, error.message.toString(), Snackbar.LENGTH_LONG).show()
                }
        }
        return root
    }

    fun goToBook() {
        findNavController().navigate(R.id.bookFragment)
    }
}