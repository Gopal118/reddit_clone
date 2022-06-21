package com.redditCloneServer.redditCloneServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class RedditCloneServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedditCloneServerApplication.class, args);
    }

}
