package org.crazymages.datingdemoapp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crazymages.datingdemoapp.entity.User;
import org.crazymages.datingdemoapp.service.database.UsersDatabaseService;
import org.crazymages.datingdemoapp.service.match.MatchService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
public class DatingAppController {

    private final MatchService matchService;
    private final UsersDatabaseService usersDatabaseService;


    @PostMapping(value = "/user/add")
    public ResponseEntity<User> insertUser(@RequestBody User user) {
        log.info("Received request to add {}", user);
        usersDatabaseService.add(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping(value = "/new-match")
    public ResponseEntity<User> getNewMatch() {
        log.info("Received request to get new match");
        User user = matchService.getNewMatch();
        log.info("Sending response with: {}", user);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/user/get-all")
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("Received request to get users list");
        List<User> userList = usersDatabaseService.getUsersList();
        if (userList != null && !userList.isEmpty()) {
            log.info("Sending response with list of users");
            return ResponseEntity.ok(userList);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable(name = "id") Integer id) throws InterruptedException {
        log.info("Received request to get user by id: {}", id);
        User user = usersDatabaseService.getUserById(id);
        log.info("Sending response with: {}", user);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.badRequest().build();
    }

    @PutMapping(value = "/user/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User user) {
        log.info("Received request to update user by id: id={}, {}", id, user);
        User updatedUser = usersDatabaseService.updateUser(id, user);
        return updatedUser != null ? ResponseEntity.ok(updatedUser) : ResponseEntity.badRequest().build();
    }

    @DeleteMapping(value = "/user/delete/by-name/{name}")
    public ResponseEntity<String> deleteByName(@PathVariable String name) {
        log.info("Received request to delete user by name: {}", name);
        usersDatabaseService.deleteByName(name);
        return ResponseEntity.ok(name);
    }

    @PutMapping(value = "/gift/{from}/{to}")
    public ResponseEntity<String> gift(@PathVariable(value = "from") Integer fromId, @PathVariable(value = "to") Integer toId) {
        log.info("Received request to transfer points from user={} to user={}", fromId, toId);
        usersDatabaseService.transferPoints(fromId, toId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(value = "/add-two-users")
    public ResponseEntity<String> addTwoUsersWithEntityManager() {
        usersDatabaseService.addTwoUsers();
        return ResponseEntity.status(HttpStatus.OK).body("Users created");
    }

}
