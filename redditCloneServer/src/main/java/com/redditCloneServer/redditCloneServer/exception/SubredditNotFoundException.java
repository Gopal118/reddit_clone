package com.redditCloneServer.redditCloneServer.exception;


public class SubredditNotFoundException extends RuntimeException {
    public SubredditNotFoundException(String message) {
        super(message);
    }
}