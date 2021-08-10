package com.example.mysql.model.issuedBook;

import com.example.mysql.model.book.Book;


public class IssuedBook extends Book {
BookStatus bookStatus;

    public BookStatus getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(BookStatus bookStatus) {
        this.bookStatus = bookStatus;
    }
}
