package com.bruno.testesunitarios.services;

import com.bruno.testesunitarios.models.domain.Post;
import com.bruno.testesunitarios.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService
{
    @Autowired
    private PostRepository postRepository;

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post findById(int id) {
        return postRepository.findById(id).orElse(null);
    }
}
