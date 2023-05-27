package org.crazymages.datingdemoapp.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crazymages.datingdemoapp.entity.User;
import org.crazymages.datingdemoapp.service.database.UsersDatabaseService;
import org.crazymages.datingdemoapp.service.match.MatchService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
public class DatingAppController {

    private final MatchService matchService;
    private final UsersDatabaseService usersDatabaseService;

    @PersistenceContext
    private final EntityManager entityManager;


    @PostMapping(value = "/user/add")
    public void insertUser(@RequestBody User user) {
        log.info("Received request to add {}", user);
        usersDatabaseService.add(user);
    }

    @GetMapping(value = "/new-match")
    public ResponseEntity<User> getNewMatch() {
        log.info("Received request to get new match");
        User user = matchService.getNewMatch();
        log.info("Sending response with: {}", user);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.badRequest().build();
    }

    @GetMapping(value = "/user/get-all")
    public List<User> getAllUsers() {
        log.info("Received request to get users list");
        List<User> userList = usersDatabaseService.getUsersList();
        log.info("Sending response with list of users");
        return userList;
    }

    @GetMapping(value = "/user/{id}")
    public User getUser(@PathVariable(name = "id") Integer id) throws InterruptedException {
        log.info("Received request to get user by id: {}", id);
        User user = usersDatabaseService.getUserById(id);
        log.info("Sending response with: {}", user);
        return user;
    }

    @PutMapping(value = "/user/update/{id}")
    public void updateUser(@PathVariable Integer id, @RequestBody User user) {
        log.info("Received request to update user by id: id={}, {}", id, user);
        usersDatabaseService.updateUser(id, user);
    }

    @DeleteMapping(value = "/user/delete/by-name/{name}")
    public void deleteByName(@PathVariable String name) {
        log.info("Received request to delete user by name: {}", name);
        usersDatabaseService.deleteByName(name);
    }

    @PutMapping(value = "/gift/{from}/{to}")
    public void gift(@PathVariable(value = "from") Integer fromId, @PathVariable(value = "to") Integer toId) {
        log.info("Received request to transfer points from user={} to user={}", fromId, toId);
        usersDatabaseService.transferPoints(fromId, toId);
    }

}
