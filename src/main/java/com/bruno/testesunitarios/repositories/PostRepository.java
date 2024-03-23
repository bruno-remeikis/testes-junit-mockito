package com.bruno.testesunitarios.repositories;

import com.bruno.testesunitarios.models.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
