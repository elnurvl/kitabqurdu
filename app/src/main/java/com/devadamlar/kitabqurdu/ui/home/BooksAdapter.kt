package com.devadamlar.kitabqurdu.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devadamlar.kitabqurdu.R
import com.devadamlar.kitabqurdu.models.Book
import com.squareup.picasso.Picasso

class BooksAdapter(val context: Context, private val listener: ItemClickListener) : RecyclerView.Adapter<BooksAdapter.BookViewHolder>() {

    var books: List<Book> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.title.text = book.title
        holder.authors.text = book.authorName?.joinToString(", ")
        holder.publisher.text = book.publisher?.get(0)
        Picasso.get()
            .load(context.getString(R.string.cover_api_url) + book.coverI + ".jpg")
            .placeholder(R.drawable.progress_animation)
            .error(R.drawable.sample_cover)
            .into(holder.thumbnail)
        holder.itemView.setOnClickListener {
            listener.onItemClicked(it, book)
        }
    }

    override fun getItemCount(): Int {
        return books.size
    }

    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val thumbnail: ImageView = itemView.findViewById(R.id.thumbnail)
        val title: TextView = itemView.findViewById(R.id.title)
        val authors: TextView = itemView.findViewById(R.id.authorsText)
        val publisher: TextView = itemView.findViewById(R.id.publisherText)
    }

    interface ItemClickListener {
        fun onItemClicked(view: View, book: Book)
    }
}