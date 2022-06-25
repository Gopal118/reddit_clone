package com.redditCloneServer.redditCloneServer.controller;

import com.redditCloneServer.redditCloneServer.dto.PostRequest;
import com.redditCloneServer.redditCloneServer.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity createPost(@RequestBody PostRequest postRequest){
        try {
            postService.save(postRequest);
           return status(HttpStatus.CREATED).body("Successfully posted");
        }catch (Exception e){
            e.printStackTrace();
            return status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to post");
        }
        }

    @GetMapping
    public ResponseEntity getAllPosts() {
        try {
        return status(HttpStatus.OK).body(postService.getAllPosts());
        }catch (Exception e){
            return status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getPost(@PathVariable Long id) {
        try {
        return status(HttpStatus.OK).body(postService.getPost(id));
    }catch (Exception e){
        return status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
    }
    }

    @GetMapping("by-subreddit/{id}")
    public ResponseEntity getPostsBySubreddit(Long id) {
        try {
        return status(HttpStatus.OK).body(postService.getPostsBySubreddit(id));
    }catch (Exception e){
        return status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @GetMapping("by-user/{name}")
    public ResponseEntity getPostsByUsername(String username) {
        try {
        return status(HttpStatus.OK).body(postService.getPostsByUsername(username));
        }catch (Exception e){
        return status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
}
