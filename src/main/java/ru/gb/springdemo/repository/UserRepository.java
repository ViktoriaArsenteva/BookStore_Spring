package ru.gb.springdemo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.springdemo.Entity.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLogin(String login);

}
