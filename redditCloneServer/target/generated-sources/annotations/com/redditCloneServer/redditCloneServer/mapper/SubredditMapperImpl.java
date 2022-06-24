package com.redditCloneServer.redditCloneServer.mapper;

import com.redditCloneServer.redditCloneServer.dto.SubredditDto;
import com.redditCloneServer.redditCloneServer.model.Subreddit;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-06-25T00:36:09+0530",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 1.8.0_171 (Oracle Corporation)"
)
@Component
public class SubredditMapperImpl implements SubredditMapper {

    @Override
    public SubredditDto mapSubredditToDto(Subreddit subreddit) {
        if ( subreddit == null ) {
            return null;
        }

        SubredditDto subredditDto = new SubredditDto();

        subredditDto.setId( subreddit.getId() );
        subredditDto.setName( subreddit.getName() );
        subredditDto.setDescription( subreddit.getDescription() );

        subredditDto.setNumberOfPosts( mapPosts(subreddit.getPosts()) );

        return subredditDto;
    }

    @Override
    public Subreddit mapDtoToSubreddit(SubredditDto subredditDto) {
        if ( subredditDto == null ) {
            return null;
        }

        Subreddit subreddit = new Subreddit();

        subreddit.setId( subredditDto.getId() );
        subreddit.setName( subredditDto.getName() );
        subreddit.setDescription( subredditDto.getDescription() );

        return subreddit;
    }
}
