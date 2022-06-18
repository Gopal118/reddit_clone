package com.redditCloneServer.redditCloneServer.repository;

import com.redditCloneServer.redditCloneServer.model.Comment;
import com.redditCloneServer.redditCloneServer.model.Post;
import com.redditCloneServer.redditCloneServer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}