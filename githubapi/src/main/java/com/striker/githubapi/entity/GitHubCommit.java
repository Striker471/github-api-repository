package com.striker.githubapi.entity;

public class GitHubCommit {

    private String sha;

    public GitHubCommit(){

    }

    public GitHubCommit(String sha) {
        this.sha = sha;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    @Override
    public String toString() {
        return "GitHubCommit{" +
                "sha='" + sha + '\'' +
                '}';
    }
}
