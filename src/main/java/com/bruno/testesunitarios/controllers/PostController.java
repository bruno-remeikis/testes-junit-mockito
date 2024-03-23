package com.bruno.testesunitarios.controllers;

import com.bruno.testesunitarios.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController
{
    @Autowired
    private PostService postService;

    @GetMapping
    private ResponseEntity findAll() {
        return ResponseEntity.ok(
            postService.findAll()
        );
    }

    @GetMapping("/{id}")
    private ResponseEntity findById(@PathVariable int id) {
        return ResponseEntity.ok(
            postService.findById(id)
        );
    }
}
