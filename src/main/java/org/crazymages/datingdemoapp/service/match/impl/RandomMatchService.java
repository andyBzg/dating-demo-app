package org.crazymages.datingdemoapp.service.match.impl;

import org.crazymages.datingdemoapp.entity.User;
import org.crazymages.datingdemoapp.service.database.UsersDatabaseService;
import org.crazymages.datingdemoapp.service.match.MatchService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@Primary
public class RandomMatchService implements MatchService {

    private final UsersDatabaseService usersDatabaseService;

    public RandomMatchService(UsersDatabaseService usersDatabaseService) {
        this.usersDatabaseService = usersDatabaseService;
    }

    @Override
    public User getNewMatch() {
        List<User> users = usersDatabaseService.getUsersList();
        Random random = new Random();
        int i = random.nextInt(users.size());
        return users.get(i);
    }
}
