package com.redditCloneServer.redditCloneServer.mapper;


import com.redditCloneServer.redditCloneServer.model.Post;
import com.redditCloneServer.redditCloneServer.dto.SubredditDto;
import com.redditCloneServer.redditCloneServer.model.Subreddit;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubredditMapper {

  @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
  SubredditDto mapSubredditToDto(Subreddit subreddit);

  default Integer mapPosts(List<Post> numberOfPosts) {
    return numberOfPosts.size();
  }

  @InheritInverseConfiguration
  @Mapping(target = "posts", ignore = true)
  Subreddit mapDtoToSubreddit(SubredditDto subredditDto);


}