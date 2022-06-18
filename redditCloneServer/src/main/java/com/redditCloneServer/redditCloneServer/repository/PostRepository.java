package com.redditCloneServer.redditCloneServer.repository;

import com.redditCloneServer.redditCloneServer.model.Post;
import com.redditCloneServer.redditCloneServer.model.Subreddit;
import com.redditCloneServer.redditCloneServer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findByUser(User user);
}