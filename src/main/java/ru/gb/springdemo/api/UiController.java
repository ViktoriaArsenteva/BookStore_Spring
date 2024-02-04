package ru.gb.springdemo.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gb.springdemo.Entity.Book;
import ru.gb.springdemo.Entity.Issue;
import ru.gb.springdemo.Entity.Reader;
import ru.gb.springdemo.service.BookService;
import ru.gb.springdemo.service.ReaderService;
import ru.gb.springdemo.service.IssuerService;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UiController {

    private final BookService books;
    private final ReaderService readers;
    private final IssuerService issues;



    @GetMapping("/ui/books")
    public String getBooks( Model model) {
        List<Book> bookList = books.getAllBooks();
        model.addAttribute("books", bookList);
        return "BookList";
    }

    @GetMapping("/ui/readers")
    public String getReaders( Model model) {
        List<Reader> readersList = readers.getAll();
        model.addAttribute("readers", readersList);
        return "ReadersList";
    }

    @GetMapping("/ui/issues")
    public String getIssues(Model model) {
        List<Issue> issueList = issues.findAllIssues();
        model.addAttribute("issues", issueList);
        return "IssueTable";
    }

    @GetMapping("/ui/reader/{id}")
    public String getReaderInfo(@PathVariable Long id, Model model) {
        Optional<Reader> reader = readers.getReaderById(id);
        List<Issue> issuesForReader = issues.getAllNotReturnedIssuesByReaderId(id);//список выдачей для читателя с невозвращенными книгами
        List<Optional<Book>> booksForReader = new ArrayList<>();
        for (Issue issue:issuesForReader) {
            booksForReader.add(books.getBookById(issue.getBookId()));
        }
        model.addAttribute("reader", reader);
        model.addAttribute("books", booksForReader);
        return "ReaderInfo";
    }





}
