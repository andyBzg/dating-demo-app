package org.crazymages.datingdemoapp.controller;

import jakarta.transaction.Transactional;
import org.crazymages.datingdemoapp.entity.User;
import org.crazymages.datingdemoapp.service.database.UsersDatabaseService;
import org.crazymages.datingdemoapp.service.match.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class DatingAppController {

    private final MatchService matchService;
    private final UsersDatabaseService usersDatabaseService;


    @Autowired
    public DatingAppController(MatchService matchService, UsersDatabaseService usersDatabaseService) {
        this.matchService = matchService;
        this.usersDatabaseService = usersDatabaseService;
    }


    @GetMapping(value = "/new-match")
    public User getNewMatch() {
        return matchService.getNewMatch();
    }

    @PostMapping(value = "/user/add")
    public void insertUser(@RequestBody User user) {
        usersDatabaseService.add(user);
    }

    @Transactional
    @DeleteMapping(value = "/user/delete/by-name/{name}")
    public void deleteByName(@PathVariable String name) {
        usersDatabaseService.deleteByName(name);
    }

}
