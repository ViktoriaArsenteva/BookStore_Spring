package ru.gb.springdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.gb.springdemo.Entity.Issue;

import java.util.List;
import java.util.Optional;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
  // В JPA репозитории необходимо только объявить интерфейс, методы будут автоматически реализованы фреймворком

  Optional<Issue> findIssueByReaderId(long readerId);

  List<Issue> findAllByReaderId(long readerId);

  //метод для нахождения невозращенных книг
  List<Issue> findAllByReaderIdAndReturnedAtIsNull(long readerId);

  // Метод для обновления даты возврата книги
  @Modifying
  @Query("UPDATE Issue i SET i.returnedAt = CURRENT_TIMESTAMP WHERE i.id = :issueId")
  void returnBook(@Param("issueId") long issueId);

  // Метод для подсчета количества не возвращенных книг у читателя
  @Query("SELECT COUNT(i) FROM Issue i WHERE i.readerId = :readerId AND i.returnedAt IS NULL")
  long countNotReturnedIssuesByReaderId(@Param("readerId") long readerId);
}