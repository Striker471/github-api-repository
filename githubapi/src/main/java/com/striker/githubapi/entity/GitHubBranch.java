package com.striker.githubapi.entity;

public class GitHubBranch {

    private String name;
    private GitHubCommit commit;

    public String getName() {
        return name;
    }

    public GitHubBranch(){

    }

    public GitHubBranch(String name, GitHubCommit commit) {
        this.name = name;
        this.commit = commit;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GitHubCommit getCommit() {
        return commit;
    }

    public void setCommit(GitHubCommit commit) {
        this.commit = commit;
    }
}
