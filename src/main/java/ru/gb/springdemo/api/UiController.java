package ru.gb.springdemo.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.gb.springdemo.model.Book;
import ru.gb.springdemo.model.Issue;
import ru.gb.springdemo.model.Reader;
import ru.gb.springdemo.repository.BookRepository;
import ru.gb.springdemo.repository.IssueRepository;
import ru.gb.springdemo.repository.ReaderRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UiController {

    private final BookRepository books;
    private final ReaderRepository readers;

    private final IssueRepository issues;

    public UiController(BookRepository repo, ReaderRepository readers, IssueRepository issues) {
        this.books = repo;
        this.readers = readers;
        this.issues = issues;
    }

    @GetMapping("/ui/books")
    public String getBooks( Model model) {
        List<Book> bookList = books.getAll();
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
        List<Issue> issueList = issues.getAllIssues();
        model.addAttribute("issues", issueList);
        return "IssueTable";
    }

    @GetMapping("/ui/reader/{id}")
    public String getReaderInfo(@PathVariable Long id, Model model) {
        Reader reader = readers.getReaderById(id);
        List<Issue> issuesForReader = issues.getNotReturnedIssuesByReaderId(id);//список выдачей для читателя с невозвращенными книгами
        List<Book> booksForReader = new ArrayList<>();
        for (Issue issue:issuesForReader) {
            booksForReader.add(books.getBookById(issue.getBookId()));
        }
        model.addAttribute("reader", reader);
        model.addAttribute("books", booksForReader);
        return "ReaderInfo";
    }





}
