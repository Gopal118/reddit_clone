package com.redditCloneServer.redditCloneServer.service;

import com.redditCloneServer.redditCloneServer.dto.SubredditDto;
import com.redditCloneServer.redditCloneServer.exception.SpringRedditException;
import com.redditCloneServer.redditCloneServer.mapper.SubredditMapper;
import com.redditCloneServer.redditCloneServer.model.Subreddit;
import com.redditCloneServer.redditCloneServer.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
        try {

            Subreddit save = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
            subredditDto.setId(save.getId());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return subredditDto;
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getALlSubreddit() {
         return subredditRepository.findAll()
                .stream().map(subredditMapper::mapSubredditToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SubredditDto getSubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id).orElseThrow(() -> new SpringRedditException("No Subreddit Found"));
            return subredditMapper.mapSubredditToDto(subreddit);
    }
}
