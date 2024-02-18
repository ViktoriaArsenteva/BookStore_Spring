package ru.gb.springdemo.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.gb.springdemo.Entity.Book;
import ru.gb.springdemo.Entity.Issue;
import ru.gb.springdemo.Entity.Reader;
import ru.gb.springdemo.repository.BookRepository;
import ru.gb.springdemo.repository.IssueRepository;
import ru.gb.springdemo.repository.ReaderRepository;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@Nested
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class ReaderControllerTest {
    @Autowired
    WebTestClient webTestClient;
    @Autowired
    ReaderRepository readerRepository;
    @Autowired
    IssueRepository issueRepository;
    @Autowired
    BookRepository bookRepository;

    @Data
    static class JUnitReaderResponse{
        private  Long id;
        private  String name;
    }
    @Data
    static class JUnitIssueResponse{
        private  Long id;
        private  Long bookId;
        private  Long readerId;
    }
//    @Data
//    static class JUnitBookResponse{
//        private Long id;
//        private String name;
//    }

    @Test
    void getById() {
        readerRepository.saveAll(List.of(
                new Reader(1L, "first"),
                new Reader(2L, "second")
        ));

        Reader reader = webTestClient.get()
                .uri("/reader/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Reader.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertEquals(1L,reader.getId());
        Assertions.assertEquals("first",reader.getName());
    }


    @Test
    void delById() {
        readerRepository.saveAll(List.of(
                new Reader(1L, "first"),
                new Reader(2L, "second")
        ));
        webTestClient.delete()
                .uri("/reader/1")
                .exchange()
                .expectStatus().isNoContent()
                .expectBody(Reader.class)
                .returnResult().getResponseBody();

        Assertions.assertFalse(readerRepository.findById(1L).isPresent());
        Assertions.assertTrue(readerRepository.findById(2L).isPresent());
    }

    @Test
    void createReader() {
        JUnitReaderResponse readerExample = new JUnitReaderResponse();
        readerExample.setName("Reader");

        JUnitReaderResponse response = webTestClient.post()
                .uri("/reader")
                .bodyValue(readerExample)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(JUnitReaderResponse.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getId());

        Assertions.assertEquals("Reader",response.getName());

        Assertions.assertTrue(readerRepository.findById(response.getId()).isPresent());
    }

    @Test
    void getAllIssues() {
        List<Reader> examplereaders = readerRepository.saveAll(List.of(
                new Reader(1L, "firstReader"),
                new Reader(2L, "secondReader")
        ));
        List<Book> exampleBooks = bookRepository.saveAll(List.of(
                new Book(1L, "firstBook"),
                new Book(2L, "secondBook")
        ));

        List<Issue> exampleIssues = issueRepository.saveAll(List.of(
                new Issue(1L,1L),
                new Issue(2L, 1L)
        ));

        List<JUnitIssueResponse> response = webTestClient.get()
                .uri("/reader/1/issue")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<JUnitIssueResponse>>(){})
                .returnResult().getResponseBody();

        Assertions.assertEquals(exampleIssues.size(), response.size());

        for (JUnitIssueResponse issueResponse : response) {
            boolean found = exampleIssues.stream()
                    .filter(it -> Objects.equals(it.getBookId(), issueResponse.getBookId()))
                    .anyMatch(it -> Objects.equals(it.getReaderId(), issueResponse.getReaderId()));
            Assertions.assertTrue(found);
        }



    }
}