package com.bruno.testesunitarios.repositories;

import com.bruno.testesunitarios.models.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer>
{
    @Query("""
        FROM User
        WHERE
            name  LIKE CONCAT('%', :search, '%') OR
            email LIKE CONCAT('%', :search, '%')
    """)
    List<User> search(String search);
}
