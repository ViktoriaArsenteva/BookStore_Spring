package ru.gb.springdemo.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "books")
@Data
public class Book {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    public Book(String name) {
        this.name = name;
    }

    public Book(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Book() {
    }



}
