package ru.gb.springdemo.repository;

import org.springframework.stereotype.Repository;
import ru.gb.springdemo.model.Issue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class IssueRepository {

  private final List<Issue> issues;

  public IssueRepository() {
    this.issues = new ArrayList<>();
  }

  public void save(Issue issue) {
    // insert into ....
    issues.add(issue);
  }

  public Issue findIssueById(long id){
    return issues.stream()
            .filter(it -> Objects.equals(it.getId(), id))
            .findFirst()
            .orElse(null);
  }

  public Issue findIssueByReaderId(long readerId){
    return issues.stream()
            .filter(it -> Objects.equals(it.getReaderId(),readerId))
            .findFirst()
            .orElse(null);
  }

  public List<Issue> getAllIssuesByReaderId(long readerId) {
    return issues.stream()
            .filter(it -> Objects.equals(it.getReaderId(),readerId))
            .collect(Collectors.toList());
  }

  public void returnBook(long issueId) {
    findIssueById(issueId).setReturnedAt(LocalDateTime.now());

  }
  /*
  Находжение количества книг, которые сейчас на руках у читателя
   */
  public long amountOfBooksByReaderId(long readerId) {
    long issueAmount = issues.stream()
                      .filter(it -> Objects.equals(it.getReaderId(),readerId))
                      .count();
    long returnAmount = issues.stream()
                        .filter(it -> Objects.equals(it.getReaderId(),readerId))
                        .filter(it -> Objects.nonNull(it.getReturnedAt()))
                        .count();
    return issueAmount - returnAmount;
  }

}
