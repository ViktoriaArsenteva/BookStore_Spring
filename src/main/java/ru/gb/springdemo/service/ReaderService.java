package ru.gb.springdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.springdemo.Entity.Reader;

import java.util.List;
import java.util.Optional;

@Service
public class ReaderService {
    private final ru.gb.springdemo.repository.ReaderRepository readerRepository;

    @Autowired
    public ReaderService(ru.gb.springdemo.repository.ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    public Optional<Reader> getReaderById(long id) {
        return readerRepository.findById(id);
    }


    public void saveReader(Reader reader) {
        readerRepository.save(reader);
    }

    public void deleteReader(long id) {
        readerRepository.deleteById(id);
    }

    public List<Reader> getAll() {
        return readerRepository.findAll();
    }

}

