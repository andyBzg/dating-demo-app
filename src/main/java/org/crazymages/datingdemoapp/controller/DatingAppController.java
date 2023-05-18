package org.crazymages.datingdemoapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.crazymages.datingdemoapp.entity.User;
import org.crazymages.datingdemoapp.service.database.UsersDatabaseService;
import org.crazymages.datingdemoapp.service.match.MatchService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
public class DatingAppController {

    private final MatchService matchService;
    private final UsersDatabaseService usersDatabaseService;


    @Autowired
    public DatingAppController(MatchService matchService, UsersDatabaseService usersDatabaseService) {
        this.matchService = matchService;
        this.usersDatabaseService = usersDatabaseService;
    }


    @PostMapping(value = "/user/add")
    public void insertUser(@RequestBody User user) {
        usersDatabaseService.add(user);
    }

    @GetMapping(value = "/new-match")
    public User getNewMatch() {
        return matchService.getNewMatch();
    }

    @GetMapping(value = "/user/get-all")
    public List<User> getAllUsers() {
        return usersDatabaseService.getUsersList();
    }

    @GetMapping(value = "/user/{id}")
    public User getUser(@PathVariable(name = "id") Integer id) throws InterruptedException {
        return usersDatabaseService.getUserById(id);
    }

    @PutMapping(value = "/user/update/{id}")
    public void updateUser(@PathVariable Integer id, @RequestBody User user) {
        usersDatabaseService.updateUser(id, user);
    }

    @DeleteMapping(value = "/user/delete/by-name/{name}")
    public void deleteByName(@PathVariable String name) {
        usersDatabaseService.deleteByName(name);
    }

    @PutMapping(value = "/gift/{from}/{to}")
    public void gift(@PathVariable(value = "from") Integer fromId, @PathVariable(value = "to") Integer toId) {
        usersDatabaseService.transferPoints(fromId, toId);
    }

}
