package com.redditCloneServer.redditCloneServer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationToken {
    private String authentication;
    private String username;
}
