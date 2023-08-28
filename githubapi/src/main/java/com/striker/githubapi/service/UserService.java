package com.striker.githubapi.service;

import com.striker.githubapi.entity.UserRepository;

import java.util.List;

public interface UserService {

    List<UserRepository> getUserRepositories(String userName, String githubToken);


}
