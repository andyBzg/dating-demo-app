package org.crazymages.datingdemoapp.controller;

import org.crazymages.datingdemoapp.entity.User;
import org.crazymages.datingdemoapp.service.match.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DatingAppController {

    private final MatchService matchService;

    @Autowired
    public DatingAppController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping(value = "/new-match")
    public User getNewMatch() {
        return matchService.getNewMatch();
    }

}
