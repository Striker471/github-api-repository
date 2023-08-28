package com.striker.githubapi.entity;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private String repository_name;

    private String owner_login;

    private List<GitHubBranch> branches;

    public UserRepository(){

        branches = new ArrayList<>();
    }

    public UserRepository(String repository_name) {
        this.repository_name = repository_name;
    }

    public String getRepository_name() {
        return repository_name;
    }

    public void setRepository_name(String repository_name) {
        this.repository_name = repository_name;
    }

    public String getOwner_login() {
        return owner_login;
    }

    public void setOwner_login(String owner_login) {
        this.owner_login = owner_login;
    }

    public List<GitHubBranch> getBranches() {
        return branches;
    }

    public void setBranches(List<GitHubBranch> branches) {
        this.branches = branches;
    }
}
