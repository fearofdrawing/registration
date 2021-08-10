package com.example.mysql;

import com.example.mysql.model.book.Book;

import java.util.List;

public interface SearchBook {
    public List<Book> searchByTitle(String title);
    public List<Book> searchByAuthor(String author);
    public List<Book> searchByYear(String year);
    public List<Book> searchByPages(String pages);
}

