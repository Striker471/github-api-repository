package com.striker.githubapi.entity;

public record GitHubBranch(
         String name,
         GitHubCommit commit
){
}