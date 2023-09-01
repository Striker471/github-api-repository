package com.striker.githubapi.service;

import com.striker.githubapi.entity.UserRepository;
import reactor.core.publisher.Flux;

import java.util.List;

public interface UserService {

    Flux<UserRepository> getUserRepositories(String userName, String githubToken);


}
