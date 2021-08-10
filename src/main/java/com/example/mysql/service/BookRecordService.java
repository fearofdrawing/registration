package com.example.mysql.service;

import com.example.mysql.model.book.Book;
import com.example.mysql.model.bookSearch.BookSearch;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookRecordService {


    Book register(Book book);
    List<Book> getAll();
    Book getById(Long id);
    Book updateBook(Long id, Book updatedBook);
    void delete(Long id);
    List <Book> search (BookSearch bookSearch);
    List<Book> findAllByTitle(String title);
    List<Book> findAllByIdBetween(Long startId, Long endId);
    List<Book> findByTitleAndAuthor( String title, String author);

}
