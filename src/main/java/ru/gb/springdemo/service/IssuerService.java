package ru.gb.springdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.gb.springdemo.Entity.Issue;
import ru.gb.springdemo.api.rest.IssueRequest;
import ru.gb.springdemo.repository.IssueRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IssuerService  {

  private final IssueRepository issueRepository;
  private final BookService bookService;
  private final ReaderService readerService;

  @Value("${application.max-allowed-books:1}")
  private long maxAllowedBooks;

  public List<Issue> findAllIssues(){
    return issueRepository.findAll();
  }
  public Optional<Issue> getIssueById(long id) {
    return issueRepository.findById(id);
  }

  public Optional<Issue> getIssueByReaderId(long readerId) {
    return issueRepository.findIssueByReaderId(readerId);
  }
  public List<Issue> getAllIssuesByReaderId(long readerId) {
    return issueRepository.findAllByReaderId(readerId);
  }

  public List<Issue> getAllNotReturnedIssuesByReaderId(long readerId) {
    return issueRepository.findAllByReaderIdAndReturnedAtIsNull(readerId);
  }

  public void returnBook(long issueId) {
    issueRepository.returnBook(issueId);
  }

  public long countNotReturnedIssuesByReaderId(long readerId) {
    return issueRepository.countNotReturnedIssuesByReaderId(readerId);
  }

  public Issue issue(IssueRequest request) {
    if (bookService.getBookById(request.getBookId()) == null) {
      throw new NoSuchElementException("Не найдена книга с идентификатором \"" + request.getBookId() + "\"");
    }
    if (readerService.getReaderById(request.getReaderId()) == null) {
      throw new NoSuchElementException("Не найден читатель с идентификатором \"" + request.getReaderId() + "\"");
    }
    if(issueRepository.countNotReturnedIssuesByReaderId(request.getReaderId()) >= maxAllowedBooks){
      throw new RuntimeException("Читатель \"" + request.getReaderId() + "не может больше взять книг" );
    }


    Issue issue = new Issue();
    issueRepository.save(issue);
    return issue;
  }

}