package com.redditCloneServer.redditCloneServer.controller;

import com.redditCloneServer.redditCloneServer.dto.SubredditDto;
import com.redditCloneServer.redditCloneServer.service.SubredditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
@Slf4j
public class SubredditController {

    private final SubredditService subredditService;

    @PostMapping
    public ResponseEntity createSubreddit(@RequestBody SubredditDto subredditDto){
        try {
          return  ResponseEntity.status(HttpStatus.CREATED).body(subredditService.save(subredditDto));
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity getAllSubreddit(){
        try {
         return  ResponseEntity.status(HttpStatus.OK).body(subredditService.getALlSubreddit());
        }catch (Exception e){
            e.printStackTrace();
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
}
