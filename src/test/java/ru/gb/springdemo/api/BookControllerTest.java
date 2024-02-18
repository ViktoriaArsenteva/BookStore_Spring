package ru.gb.springdemo.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.gb.springdemo.Entity.Book;
import ru.gb.springdemo.repository.BookRepository;

import java.util.List;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class BookControllerTest {

    @Autowired
    WebTestClient webTestClient;
    @Autowired
    BookRepository bookRepository;



    @Data
    static class JUnitBookResponse{
        private Long id;
        private String name;
    }

    @Test
    void testGetById(){
        bookRepository.saveAll(List.of(
                new Book(1L, "first"),
                new Book(2L, "second")
        ));

        Book book = (Book) webTestClient.get()
                .uri("/book/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertEquals(1L,book.getId());
        Assertions.assertEquals("first",book.getName());
    }

    @Test
    void createBook(){
        JUnitBookResponse book = new JUnitBookResponse();
        //book.setId(5L);
        book.setName("CreatedBook");

        JUnitBookResponse response = webTestClient.post()
                .uri("/book")
                .bodyValue(book)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(JUnitBookResponse.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getId());

        Assertions.assertEquals("CreatedBook",response.getName());

        Assertions.assertTrue(bookRepository.findById(response.getId()).isPresent());

    }

    @Test
    void deleteBook(){
        List<Book> example = bookRepository.saveAll(List.of(
                new Book(1L, "first"),
                new Book(2L, "second")
        ));

        webTestClient.delete()
                .uri("/book/1")
                .exchange()
                .expectStatus().isNoContent()
                .expectBody(JUnitBookResponse.class)
                .returnResult().getResponseBody();

        Assertions.assertFalse(bookRepository.findById(1L).isPresent());
        Assertions.assertTrue(bookRepository.findById(2L).isPresent());
    }

}