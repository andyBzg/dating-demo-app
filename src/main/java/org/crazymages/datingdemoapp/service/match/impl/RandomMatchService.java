package org.crazymages.datingdemoapp.service.match.impl;

import lombok.extern.slf4j.Slf4j;
import org.crazymages.datingdemoapp.entity.User;
import org.crazymages.datingdemoapp.service.database.UsersDatabaseService;
import org.crazymages.datingdemoapp.service.match.MatchService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Slf4j
@Service
@Primary
public class RandomMatchService implements MatchService {

    private final UsersDatabaseService usersDatabaseService;

    public RandomMatchService(UsersDatabaseService usersDatabaseService) {
        this.usersDatabaseService = usersDatabaseService;
    }

    @Override
    @Transactional
    public User getNewMatch() {
        List<User> users = usersDatabaseService.getUsersList();
        Random random = new Random();
        int i = random.nextInt(users.size());
        log.info("Found new match with user: id={}", i);
        return users.get(i);
    }
}
