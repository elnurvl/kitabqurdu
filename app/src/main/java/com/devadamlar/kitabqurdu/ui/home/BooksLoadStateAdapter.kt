package com.devadamlar.kitabqurdu.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.devadamlar.kitabqurdu.R

class BooksLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<BooksLoadStateAdapter.BooksLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: BooksLoadStateViewHolder, loadState: LoadState) {
        holder.retry.setOnClickListener {
            retry.invoke()
        }
        if (loadState is LoadState.Error) {
            holder.error.text = loadState.error.localizedMessage
        }
        holder.progress.isVisible = loadState is LoadState.Loading
        holder.retry.isVisible = loadState !is LoadState.Loading
        holder.error.isVisible = loadState !is LoadState.Loading
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): BooksLoadStateViewHolder {
        return BooksLoadStateViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.books_load_state_footer_view_item,
                parent,
                false,
            ),
        )
    }

    inner class BooksLoadStateViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        val retry: Button = itemView.findViewById(R.id.retryButton)
        val error: TextView = itemView.findViewById(R.id.error_msg)
        val progress: ProgressBar = itemView.findViewById(R.id.progressBar)
    }
}