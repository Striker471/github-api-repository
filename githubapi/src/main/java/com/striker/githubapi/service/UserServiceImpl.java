package com.striker.githubapi.service;

import com.striker.githubapi.entity.GitHubBranch;
import com.striker.githubapi.entity.RepositoryData;
import com.striker.githubapi.entity.UserRepository;
import com.striker.githubapi.rest.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final RestTemplate restTemplate;
    private final WebClient webClient;

    @Autowired
    public UserServiceImpl(RestTemplate restTemplate, WebClient webClient) {
        this.restTemplate = restTemplate;
        this.webClient = webClient;
    }

    @Override
    public Flux<UserRepository> getUserRepositories(String userName, String githubToken) {

        String path = "https://api.github.com/users/" + userName + "/repos";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + githubToken);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<RepositoryData[]> response;
        try {
            response = restTemplate.exchange(
                    path,
                    HttpMethod.GET,
                    entity,
                    RepositoryData[].class);
        } catch (Exception e) {
            throw new UserNotFoundException("User Not Found:  " + e.getMessage());
        }
        RepositoryData[] repositoryDatum1s = response.getBody();

        List<RepositoryData> repositoryDataList = new ArrayList<>(Arrays.asList(repositoryDatum1s));

        // filtering out repositories from forks

        List<RepositoryData> noForksList = repositoryDataList
                .stream()
                .filter(repositoryFilter -> !repositoryFilter.fork())
                .toList();

        // for each repo get sha and name branch

        return Flux.fromIterable(noForksList)
                .flatMap(data -> {
                    return webClient.get()
                            .uri("/repos/{userName}/{repoName}/branches", userName, data.name())
                            .header("Authorization", "Bearer " + githubToken)
                            .retrieve()
                            .bodyToFlux(GitHubBranch.class)
                            .collectList()
                            .map(branches -> new UserRepository(
                                    data.name(),
                                    userName,
                                    branches
                            ));
                });
    }
}
