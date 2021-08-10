package com.example.mysql.service;

import com.example.mysql.exception.BadRequestException;
import com.example.mysql.exception.NotFoundException;
import com.example.mysql.model.book.Book;
import com.example.mysql.model.bookSearch.BookSearch;
import com.example.mysql.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static org.springframework.data.domain.ExampleMatcher.matchingAll;

@Transactional
@Service
@AllArgsConstructor
public class BookDBService implements BookRecordService {

    private final BookRepository bookRepository;

    @Override
    public Book register(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> search(BookSearch bookSearch) {
        Book book = new Book();
        book.setTitle(bookSearch.getTitle());
        book.setAuthor(bookSearch.getAuthor());
        book.setYear(bookSearch.getYear());
        book.setPages(bookSearch.getPages());

        Example<Book> studentExample = Example.of(book, matchingAll().withIgnoreNullValues());
        return bookRepository.findAll(studentExample);
    }


    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book getById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Book record does not exist"));
    }

    @Override
    public Book updateBook(Long id, Book updatedBook) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Invalid book id " + id));

        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setYear(updatedBook.getYear());
        existingBook.setPages(updatedBook.getPages());

        return bookRepository.save(existingBook);

    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<Book> findAllByTitle(String title) {
        return bookRepository.findAllByTitle(title);
    }

    @Override
    public List<Book> findAllByIdBetween(Long startId, Long endId){
        List <Book> result = bookRepository.findAllByIdBetween(startId,endId);
        if (result.isEmpty()){
            throw new BadRequestException("There is no book with such id");
        }
        return result;
    }

    @Override
    public  List<Book> findByTitleAndAuthor( String title, String author){
        List <Book> result = bookRepository.findByTitleAndAuthor(title,author);
        if (result.isEmpty()){
            throw new BadRequestException("There is no such books");
        }
        return result;
    }
}
