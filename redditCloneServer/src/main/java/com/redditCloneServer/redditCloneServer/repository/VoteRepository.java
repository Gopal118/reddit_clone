package com.redditCloneServer.redditCloneServer.repository;

import com.redditCloneServer.redditCloneServer.model.Post;
import com.redditCloneServer.redditCloneServer.model.User;
import com.redditCloneServer.redditCloneServer.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}