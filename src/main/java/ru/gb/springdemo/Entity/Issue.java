package ru.gb.springdemo.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "issues")
@Data
public class Issue {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long bookId;

    @Column(nullable = false)
    private Long readerId;

    @Column(nullable = false)
    private LocalDateTime issuedAt;

    @Column(nullable = true)
    private LocalDateTime returnedAt;
}