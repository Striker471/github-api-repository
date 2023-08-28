package com.striker.githubapi.service;

import com.striker.githubapi.entity.GitHubBranch;
import com.striker.githubapi.entity.RepositoryData;
import com.striker.githubapi.entity.UserRepository;
import com.striker.githubapi.rest.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final RestTemplate restTemplate;

    @Autowired
    public UserServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public List<UserRepository> getUserRepositories(String userName, String githubToken){

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
        }catch (Exception e){
            throw new UserNotFoundException("User Not Found:  " + e.getMessage());
        }
        RepositoryData[] repositoryData = response.getBody();

        List<RepositoryData> repositoryDataList = new ArrayList<>(Arrays.asList(repositoryData));

        // filtering out repositories from forks

        List<RepositoryData> noForksList = repositoryDataList
                .stream()
                .filter(repositoryFilter -> !repositoryFilter.isFork())
                .toList();

        // for each repo get sha and name branch

        List<UserRepository> userRepositories = new ArrayList<>();
        for(RepositoryData data : noForksList)
        {
            String brachesPath = "https://api.github.com/repos/" + userName + "/" + data.getName() + "/branches";
            ResponseEntity<GitHubBranch[]> repoResponseData =
                    restTemplate.exchange(
                            brachesPath,
                            HttpMethod.GET,
                            entity,
                            GitHubBranch[].class);
            GitHubBranch[] gitHubBranch = repoResponseData.getBody();


            List<GitHubBranch> gitHubBranchList = new ArrayList<>(Arrays.asList(gitHubBranch));

            UserRepository tempUserRepository = new UserRepository();
            tempUserRepository.setRepository_name(data.getName());
            tempUserRepository.setOwner_login(userName);
            tempUserRepository.setBranches(gitHubBranchList);
            userRepositories.add(tempUserRepository);

        }

        return userRepositories;
    }

}
