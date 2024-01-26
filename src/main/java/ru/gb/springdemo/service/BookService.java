package ru.gb.springdemo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.springdemo.Entity.Book;
import ru.gb.springdemo.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final ru.gb.springdemo.repository.BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Optional<Book> getBookById(long id) {
        return bookRepository.findById(id);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public void save(Book book) {
        bookRepository.save(book);
    }

    public void deleteBookById(long id) {
        bookRepository.deleteById(id);
    }

}
