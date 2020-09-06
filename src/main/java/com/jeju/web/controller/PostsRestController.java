package com.jeju.web.controller;

import java.util.List;
import java.util.Optional;

import com.jeju.web.domain.posts.Posts;
import com.jeju.web.domain.posts.PostsRepository;
import com.jeju.web.dto.posts.PostsSaveRequestDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@AllArgsConstructor
public class PostsRestController {
    private PostsRepository postsRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void cleanup() {
        postsRepository.deleteAll();
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }

    @PostMapping(value = "/posts")
    public void savePosts(@RequestBody PostsSaveRequestDto dto) {
        // TODO: process POST request
        postsRepository.save(dto.toEntity());
        Optional<Posts> post = postsRepository.findById(1L);
        logger.info("타이틀 :: {}", post.get().getTitle());
    }

}
