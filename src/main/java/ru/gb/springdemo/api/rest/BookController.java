package ru.gb.springdemo.api.rest;

import org.springframework.web.bind.annotation.*;
import ru.gb.springdemo.Entity.Book;
import ru.gb.springdemo.service.BookService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService repo;

    public BookController(BookService repo) {
        this.repo = repo;
    }

    @GetMapping(value = "/{id}")
    public Optional<Book> getBookById (@PathVariable long id){
        return repo.getBookById(id);
    }

    @GetMapping(value = "/all")
    public List<Book> getAllBooks(){
        return repo.getAllBooks();
    }

    @PostMapping()
    public Book addNewBook(@RequestBody Book book){
        repo.save(book);
        return book;
    }

    @DeleteMapping(value = "/delete/{id}")
    public Optional<Book> deleteBook(@PathVariable long id){
        Optional<Book> book = repo.getBookById(id);
        repo.deleteBookById(id);
        return book;
    }

}
