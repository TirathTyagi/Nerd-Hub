package com.internshala.nerdshub.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.internshala.nerdshub.R
import com.internshala.nerdshub.database.BookEntity
import com.squareup.picasso.Picasso
import org.w3c.dom.Text
import java.util.ArrayList

class FavouriteRecyclerAdapter(val context:Context, val bookList : List<BookEntity>):RecyclerView.Adapter<FavouriteRecyclerAdapter.FavouriteViewHolder>() {
    class FavouriteViewHolder(view: View):RecyclerView.ViewHolder(view)
    {
        val favBookName:TextView = view.findViewById(R.id.txtfavBookName)
        val favBookAuthor:TextView = view.findViewById(R.id.txtfavAuthorName)
        val favBookPrice:TextView = view.findViewById(R.id.txtfavBookPrice)
        val favBookRating:TextView = view.findViewById(R.id.txtfavBookRating)
        val imgFavBook:ImageView = view.findViewById(R.id.imgIconBook)
        val FavLayout:RelativeLayout = view.findViewById(R.id.favRelativeLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_favourite_single_row,parent,false)
        return FavouriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val book = bookList[position]
        holder.favBookName.text = book.bookName
        holder.favBookAuthor.text = book.bookAuthor
        holder.favBookPrice.text = book.bookPrice
        holder.favBookRating.text = book.bookRating
        Picasso.get().load(book.bookImage).error(R.drawable.book_icon).into(holder.imgFavBook)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }
}