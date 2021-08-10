package com.example.mysql;

import com.example.mysql.model.book.Book;

import java.util.HashMap;
import java.util.List;

public class Catalog implements SearchBook {
    private HashMap<String, List<Book>> bookTitles;
    private HashMap<String, List<Book>> bookAuthors;
    private HashMap<Long, List<Book>> bookYears;
    private HashMap<Long, List<Book>> bookPages;

    @Override
    public List<Book> searchByTitle(String query) {
        return bookTitles.get(query);
    }

    @Override
    public List<Book> searchByAuthor(String query) {
        return bookAuthors.get(query);
    }

    @Override
    public List<Book> searchByYear(String query) {
        return bookYears.get(query);
    }

    @Override
    public List<Book> searchByPages(String query) {
        return bookPages.get(query);
    }
}
