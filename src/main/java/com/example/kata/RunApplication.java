package com.example.kata;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class RunApplication {

    static final String URL = "http://94.198.50.185:7081/api/users";
    private final RestTemplate restTemplate = new RestTemplate();
    private HttpHeaders headers = new HttpHeaders();
    private static String result = "";

    public static void main(String[] args) {

        RunApplication runApplication = new RunApplication();
        runApplication.initSessionId();
        runApplication.addUser();
        runApplication.updateUser();
        runApplication.deleteUser();
        System.out.println("Искомый код: " + result);


    }

    private void initSessionId () {
        ResponseEntity<String> forEntity = restTemplate.getForEntity(URL, String.class);
        List<String> cookies = forEntity.getHeaders().get("Set-Cookie");
        headers.set("Cookie",cookies.stream().collect(Collectors.joining(";")));
    }

    private void addUser () {
        User user = new User(3L, "James","Brown", (byte) 25);
        HttpEntity<User> requestBody = new HttpEntity<>(user, headers);
        String request = restTemplate.postForEntity(URL, requestBody, String.class).getBody();
        result = result + request;
    }

    private void updateUser () {
        User user = new User(3L, "Thomas","Shelby", (byte) 25);
        HttpEntity<User> requestBody = new HttpEntity<>(user, headers);
        String request = restTemplate.exchange(URL, HttpMethod.PUT, requestBody, String.class).getBody();
        result = result + request;
    }

    private void deleteUser () {
        HttpEntity<User> requestBody = new HttpEntity<>(headers);
        String request = restTemplate.exchange(URL + "/3", HttpMethod.DELETE, requestBody, String.class).getBody();
        result = result + request;
    }
}