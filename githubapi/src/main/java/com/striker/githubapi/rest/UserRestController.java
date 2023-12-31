package com.striker.githubapi.rest;

import com.striker.githubapi.entity.UserRepository;
import com.striker.githubapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserRestController {

    private UserService userService;

    @Value("${github.token}")
    private String githubToken;

    @Autowired
    UserRestController(UserService theUserService) {
        userService = theUserService;

    }

    @GetMapping(
            value = "/users/{userName}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Flux<UserRepository> getUserByUsername(@PathVariable String userName) {

        return userService.getUserRepositories(userName, githubToken);

    }

}
