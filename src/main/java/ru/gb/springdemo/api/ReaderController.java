package ru.gb.springdemo.api;

import org.springframework.web.bind.annotation.*;
import ru.gb.springdemo.model.Issue;
import ru.gb.springdemo.model.Reader;
import ru.gb.springdemo.repository.IssueRepository;
import ru.gb.springdemo.repository.ReaderRepository;


import java.util.List;
@RestController
@RequestMapping("/reader")
public class ReaderController {

    private final ReaderRepository repo;
    private final IssueRepository issueRepo;

    public ReaderController(ReaderRepository repo, IssueRepository issueRepo) {
        this.repo = repo;
        this.issueRepo = issueRepo;
    }

    @GetMapping(value = "/{id}")
    public Reader getReaderById (@PathVariable long id){
        return repo.getReaderById(id);
    }

    @GetMapping(value = "/all")
    public List<Reader> getAllReaders(){
        return repo.getAll();
    }

    @GetMapping(value = "/{id}/issue")
    public List<Issue> getIssuesByReaderId(@PathVariable("id") long readerId){
        return issueRepo.getAllIssuesByReaderId(readerId);
    }

    @PostMapping()
    public Reader addNewReader(@RequestBody Reader reader){
        repo.addReader(reader);
        return reader;
    }

    @DeleteMapping(value = "/delete/{id}")
    public Reader deleteReader(@PathVariable long id){
        Reader reader = repo.getReaderById(id);
        repo.deleteReader(reader);
        return reader;
    }
}
