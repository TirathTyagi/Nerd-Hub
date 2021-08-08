package com.internshala.nerdshub.Activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.media.Image
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.internshala.nerdshub.R
import com.internshala.nerdshub.database.BookDatabase
import com.internshala.nerdshub.database.BookEntity
import com.internshala.nerdshub.util.ConnectionManager
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject
import org.w3c.dom.Text

class DescriptionActivity : AppCompatActivity() {
    lateinit var bookName:TextView
    lateinit var bookAuthorName:TextView
    lateinit var bookPriceAmt:TextView
    lateinit var bookRatingAmt:TextView
    lateinit var imgBookDes:ImageView
    lateinit var txtBookDesc:TextView
    lateinit var btnAddtoFav:Button
    lateinit var progressBarDesc:ProgressBar
    lateinit var progressLayoutDesc:RelativeLayout
    lateinit var toolbarDesc:Toolbar
    var bookId:String? = "100"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
        bookName = findViewById(R.id.txtBookName)
        bookAuthorName=findViewById(R.id.txtBookAuthor)
        bookPriceAmt=findViewById(R.id.txtBookPrice)
        bookRatingAmt=findViewById(R.id.txtBookRating)
        imgBookDes=findViewById(R.id.imgBookimage)
        txtBookDesc=findViewById(R.id.txtBookDesc)
        btnAddtoFav=findViewById(R.id.btnAddToFav)
        progressBarDesc=findViewById(R.id.progressDesc)
        progressLayoutDesc=findViewById(R.id.progressBarDesc)
        progressBarDesc.visibility = View.VISIBLE
        progressLayoutDesc.visibility = View.VISIBLE
        toolbarDesc = findViewById(R.id.descToolbar)
        setSupportActionBar(toolbarDesc)
        supportActionBar?.title = "Book Description"
        if(intent!=null)
        {
            bookId = intent.getStringExtra("book_id")
        }
        else if(bookId == "100")
        {
            finish()
            Toast.makeText(this@DescriptionActivity,"Unexpected error occured!",Toast.LENGTH_SHORT).show()
        }
        else
        {
            finish()
            Toast.makeText(this@DescriptionActivity,"Unexpected error occured!",Toast.LENGTH_SHORT).show()
        }
        val queue = Volley.newRequestQueue(this@DescriptionActivity)
        val url = "http://13.235.250.119/v1/book/get_book/"
        val jsonParams = JSONObject()
        jsonParams.put("book_id",bookId)
        if(ConnectionManager().checkConnectivity(this@DescriptionActivity)) {
            val jsonRequest =
                object : JsonObjectRequest(Request.Method.POST, url, jsonParams, Response.Listener {
                    try {
                        val success = it.getBoolean("success")
                        if (success) {
                            val jsonBookObject = it.getJSONObject("book_data")
                            progressLayoutDesc.visibility = View.GONE
                            progressBarDesc.visibility = View.GONE
                            val imageUrl = jsonBookObject.getString("image")
                            Picasso.get().load(jsonBookObject.getString("image"))
                                .error(R.drawable.book_app_icon_web).into(imgBookDes)
                            bookName.text = jsonBookObject.getString("name")
                            bookAuthorName.text = jsonBookObject.getString("author")
                            bookRatingAmt.text = jsonBookObject.getString("rating")
                            bookPriceAmt.text = jsonBookObject.getString("price")
                            txtBookDesc.text = jsonBookObject.getString("description")
                            val bookEntity = BookEntity(
                                bookId?.toInt() as Int,
                                bookName.text.toString(),
                                bookAuthorName.text.toString(),
                                bookPriceAmt.text.toString(),
                                bookRatingAmt.text.toString(),
                                txtBookDesc.text.toString(),
                                imageUrl
                            )
                            val checkFav = DBASyncTask(applicationContext,bookEntity,1).execute()
                            val isFav = checkFav.get()
                            if(isFav)
                            {
                                btnAddtoFav.text = "Remove From Favourites"
                                val favColor = ContextCompat.getColor(applicationContext,R.color.colorFavourite)
                                btnAddtoFav.setBackgroundColor(favColor)
                            }
                            else
                            {
                                btnAddtoFav.text = "Add to Favourites"
                                val nofavcolor = ContextCompat.getColor(applicationContext,R.color.design_default_color_on_primary)
                                btnAddtoFav.setBackgroundColor(nofavcolor)
                            }
                            btnAddtoFav.setOnClickListener {
                                if(DBASyncTask(applicationContext,bookEntity,1).execute().get())
                                {
                                    val async = DBASyncTask(applicationContext,bookEntity,3).execute()
                                    val res = async.get()
                                    if(res)
                                    {
                                        Toast.makeText(this@DescriptionActivity,"Book removed from Favourites",Toast.LENGTH_SHORT).show()
                                        btnAddtoFav.text = "Add to Favourites"
                                        val favColor = ContextCompat.getColor(applicationContext,R.color.colorFavourite)
                                        btnAddtoFav.setBackgroundColor(favColor)
                                    }
                                    else
                                    {
                                        Toast.makeText(this@DescriptionActivity,"Some Error occured",Toast.LENGTH_SHORT)
                                    }
                                }
                                else
                                {
                                    val async = DBASyncTask(applicationContext,bookEntity,2).execute()
                                    val res = async.get()
                                    if(res)
                                    {
                                        Toast.makeText(this@DescriptionActivity,"Book added to Favourites",Toast.LENGTH_SHORT).show()
                                        btnAddtoFav.text = "Remove From Favourites"
                                        val nofavColor = ContextCompat.getColor(applicationContext,R.color.design_default_color_on_primary)
                                        btnAddtoFav.setBackgroundColor(nofavColor)
                                    }
                                    else
                                    {
                                        Toast.makeText(this@DescriptionActivity,"Some Error occured",Toast.LENGTH_SHORT)
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(
                                this@DescriptionActivity,
                                "Unexpected error occured!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: JSONException) {
                        Toast.makeText(
                            this@DescriptionActivity,
                            "Unexpected error occured!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }, Response.ErrorListener {
                    Toast.makeText(
                        this@DescriptionActivity,
                        "Volley error occured!",
                        Toast.LENGTH_SHORT
                    ).show()
                }) {
                    override fun getHeaders(): MutableMap<String, String> {
                        val headers = HashMap<String, String>()
                        headers["Content-type"] = "application/json"
                        headers["token"] = "330ce002cfe617"
                        return headers
                    }
                }
            queue.add(jsonRequest)
        }
        else
        {
            val dialog = AlertDialog.Builder(this@DescriptionActivity)
            dialog.setTitle("ERROR!!!")
            dialog.setMessage("Not Connected to the Internet.")
            dialog.setPositiveButton("Fix") { text, listener ->
                val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingsIntent)
                finish() // So that the app is reopened and list is refreshed
            }
            dialog.setNegativeButton("Exit") { text, listener ->
                ActivityCompat.finishAffinity(this@DescriptionActivity) // This closes the app.
            }
            dialog.create()
            dialog.show()
        }
    }
    class DBASyncTask(val context:Context,val bookEntity: BookEntity,val mode:Int): AsyncTask<Void, Void, Boolean>() {
        /*
        Mode - 1 -> Check DB if the book is in favourite or not
        Mode - 2 -> Save the book into DB as favourite
        Mode - 3 -> Remove a book that is already added from favourite.
         */
        val db = Room.databaseBuilder(context,BookDatabase::class.java,"books-db").build()
        override fun doInBackground(vararg params: Void?): Boolean {
            when (mode) {
                1 -> {
                    val book:BookEntity? = db.bookDao().checkIfFav(bookEntity.book_id.toString())
                    db.close()
                    return book !=null
                }
                2 -> {
                    db.bookDao().insertBook(bookEntity)
                    db.close()
                    return true
                }
                3 -> {
                    db.bookDao().deleteBook(bookEntity)
                    db.close()
                    return true
                }
            }
            return false
        }
    }
}
