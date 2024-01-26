package ru.gb.springdemo.api.rest;

import org.springframework.web.bind.annotation.*;
import ru.gb.springdemo.Entity.Issue;
import ru.gb.springdemo.Entity.Reader;
import ru.gb.springdemo.service.IssuerService;
import ru.gb.springdemo.service.ReaderService;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reader")
public class ReaderController {

    private final ReaderService repo;
    private final IssuerService issueRepo;

    public ReaderController(ReaderService repo, IssuerService issueRepo) {
        this.repo = repo;
        this.issueRepo = issueRepo;
    }

    @GetMapping(value = "/{id}")
    public Optional<Reader> getReaderById (@PathVariable long id){
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
        repo.saveReader(reader);
        return reader;
    }

    @DeleteMapping(value = "/delete/{id}")
    public Optional<Reader> deleteReader(@PathVariable long id){
        Optional<Reader> reader = repo.getReaderById(id);
        repo.deleteReader(id);
        return reader;
    }
}
