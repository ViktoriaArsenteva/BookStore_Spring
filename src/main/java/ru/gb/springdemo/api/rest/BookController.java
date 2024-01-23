package ru.gb.springdemo.api.rest;

import org.springframework.web.bind.annotation.*;
import ru.gb.springdemo.model.Book;
import ru.gb.springdemo.repository.BookRepository;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookRepository repo;

    public BookController(BookRepository repo) {
        this.repo = repo;
    }

    @GetMapping(value = "/{id}")
    public Book getBookById (@PathVariable long id){
        return repo.getBookById(id);
    }

    @GetMapping(value = "/all")
    public List<Book> getAllBooks(){
        return repo.getAll();
    }

    @PostMapping()
    public Book addNewBook(@RequestBody Book book){
        repo.addBook(book);
        return book;
    }

    @DeleteMapping(value = "/delete/{id}")
    public Book deleteBook(@PathVariable long id){
        Book book = repo.getBookById(id);
        repo.deleteBook(book);
        return book;
    }

}
