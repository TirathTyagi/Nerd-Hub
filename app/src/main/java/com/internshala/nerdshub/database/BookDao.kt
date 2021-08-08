package com.internshala.nerdshub.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookDao {
    @Insert
    fun insertBook(book:BookEntity)
    @Delete
    fun deleteBook(book:BookEntity)
    @Query("SELECT * FROM books")
    fun getAllBooks():List<BookEntity>
    @Query("SELECT * FROM books WHERE book_id = :bookId")
    fun checkIfFav(bookId:String):BookEntity
}