package com.internshala.nerdshub.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.internshala.nerdshub.Activity.DescriptionActivity
import com.internshala.nerdshub.Models.Book
import com.internshala.nerdshub.R
import com.squareup.picasso.Picasso

class DashboardRecyclerAdapter(val Context: Context,val itemList:ArrayList<Book>):
    RecyclerView.Adapter<DashboardRecyclerAdapter.DashboardViewHolder>() {
    class DashboardViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        val textView:TextView = view.findViewById(R.id.txtRecyclerView)
        val imgBook : ImageView = view.findViewById(R.id.imgBook)
        val textAuthor:TextView = view.findViewById(R.id.txtAuthor)
        val textRating:TextView = view.findViewById(R.id.txtRating)
        val textPrice:TextView = view.findViewById(R.id.txtPrice)
        val dashboardLayout:RelativeLayout = view.findViewById(R.id.dashboardViewLayout)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycley_view_dashboard,parent,false)
        return DashboardViewHolder(view)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val book = itemList[position]
        holder.textView.text = book.bookName
        holder.textAuthor.text = book.bookAuthor
        holder.textPrice.text = book.bookPrice
        holder.textRating.text = book.bookRating
        Picasso.get().load(book.bookImage).error(R.drawable.book_icon).into(holder.imgBook)
        holder.dashboardLayout.setOnClickListener{
            val intent = Intent(Context,DescriptionActivity::class.java)
            intent.putExtra("book_id",book.bookId)
            Context.startActivity(intent) // Using Context as every method related to current activity is accessed like this
        }
    }

    override fun getItemCount(): Int {
       return itemList.size
    }
}