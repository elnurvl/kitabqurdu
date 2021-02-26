package com.devadamlar.kitabqurdu.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devadamlar.kitabqurdu.ActivityViewModel
import com.devadamlar.kitabqurdu.App
import com.devadamlar.kitabqurdu.MainActivity
import com.devadamlar.kitabqurdu.R
import com.devadamlar.kitabqurdu.models.Book
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

        val adapter = BooksAdapter(requireContext(), object : BooksAdapter.ItemClickListener {
            override fun onItemClicked(view: View, book: Book) {
                Log.d("Home", "Selected book: ${book.key}")
                activityViewModel.selectedBook.onNext(book)
                goToBook()
            }

        })
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        searchButton.setOnClickListener {
            // TODO: Show progressbar, "Not found" and error messages when necessary
            if (searchText.text.toString().isEmpty()) return@setOnClickListener
            (activity as MainActivity).hideKeyboard()
            homeViewModel.search(searchText.text.toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    adapter.submitData(lifecycle, it)
                }
        }
        return root
    }

    fun goToBook() {
        findNavController().navigate(R.id.bookFragment)
    }
}