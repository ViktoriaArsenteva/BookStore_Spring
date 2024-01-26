package ru.gb.springdemo.api.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.springdemo.Entity.Issue;
import ru.gb.springdemo.service.IssuerService;

import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/issue")
public class IssueController {

  @Autowired
  private IssuerService service;

  private final IssuerService repo;

  public IssueController(IssuerService repo) {
    this.repo = repo;
  }

  /*
  Возврат книги
   */
  @PutMapping("/{issueId}")
  public void returnBook(@PathVariable long issueId) {
    repo.returnBook(issueId);
  }

  /*
  Поиск запроса на выдачу книги по идентификатору
   */
  @GetMapping(value = "/{id}")
  public Optional<Issue> getIssueInfoById(@PathVariable long id){
    return repo.getIssueById(id);
  }

/*
запрос на выдачу книги
 */
  @PostMapping
  public ResponseEntity<Issue> issueBook(@RequestBody IssueRequest request) {
    log.info("Получен запрос на выдачу: readerId = {}, bookId = {}", request.getReaderId(), request.getBookId());

    final Issue issue;
    try {
      issue = service.issue(request);
    } catch (NoSuchElementException e) {
      return ResponseEntity.notFound().build();
    }catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();

    }

    return ResponseEntity.status(HttpStatus.CREATED).body(issue);
  }

}
