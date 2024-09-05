package me.romo.sgstock.repository;

import me.romo.sgstock.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPublicId(String publicId);

    boolean existsByPublicId(String publicId);
}
