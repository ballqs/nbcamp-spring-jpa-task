package com.sparta.nbcampspringjpatask.repository;

import com.sparta.nbcampspringjpatask.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
