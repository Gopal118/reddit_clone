package com.redditCloneServer.redditCloneServer.service;

import com.redditCloneServer.redditCloneServer.dto.PostRequest;
import com.redditCloneServer.redditCloneServer.dto.PostResponse;
import com.redditCloneServer.redditCloneServer.exception.PostNotFoundException;
import com.redditCloneServer.redditCloneServer.exception.SpringRedditException;
import com.redditCloneServer.redditCloneServer.exception.SubredditNotFoundException;
import com.redditCloneServer.redditCloneServer.mapper.PostMapper;
import com.redditCloneServer.redditCloneServer.model.Post;
import com.redditCloneServer.redditCloneServer.model.Subreddit;
import com.redditCloneServer.redditCloneServer.model.User;
import com.redditCloneServer.redditCloneServer.repository.PostRepository;
import com.redditCloneServer.redditCloneServer.repository.SubredditRepository;
import com.redditCloneServer.redditCloneServer.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final PostMapper postMapper;

    public void save(PostRequest postRequest) {

        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new SpringRedditException(postRequest.getSubredditName() + " Post not found"));

        User currentUser = authService.getCurrentUser();


         postRepository.save(postMapper.map(postRequest,subreddit,currentUser));
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubreddit(Long subredditId) {
        Subreddit subreddit = subredditRepository.findById(subredditId)
                .orElseThrow(() -> new SubredditNotFoundException(subredditId.toString()));
        List<Post> posts = postRepository.findAllBySubreddit(subreddit);
        return posts.stream().map(postMapper::mapToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }
}
