package ru.gb.springdemo.Entity;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Table(name = "readers")
@Data
public class Reader {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    public Reader(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Reader(String name) {
        this.name = name;
    }
}
