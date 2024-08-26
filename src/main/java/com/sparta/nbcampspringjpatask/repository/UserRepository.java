package com.sparta.nbcampspringjpatask.repository;

import com.sparta.nbcampspringjpatask.entity.Schedule;
import com.sparta.nbcampspringjpatask.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByName(String name);
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    default User findByIdOrElseThrow(long id) {
        return findById(id).orElseThrow(() -> new NoSuchElementException("해당 유저는 존재하지 않습니다."));
    }
}
