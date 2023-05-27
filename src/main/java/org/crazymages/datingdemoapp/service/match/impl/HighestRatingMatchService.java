package org.crazymages.datingdemoapp.service.match.impl;

import lombok.extern.slf4j.Slf4j;
import org.crazymages.datingdemoapp.entity.User;
import org.crazymages.datingdemoapp.service.database.UsersDatabaseService;
import org.crazymages.datingdemoapp.service.match.MatchService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
//@Primary
public class HighestRatingMatchService implements MatchService {

    private final UsersDatabaseService usersDatabaseService;

    public HighestRatingMatchService(UsersDatabaseService usersDatabaseService) {
        this.usersDatabaseService = usersDatabaseService;
    }

    @Override
    @Transactional
    public User getNewMatch() {
        List<User> users = usersDatabaseService.getUsersList();
        int maxRating = 0;
        User highestRatedUser = null;

        for (User user : users) {
            if (user.getRating() > maxRating) {
                maxRating = user.getRating();
                highestRatedUser = user;
            }
        }
        log.info("Found new match with user: {}", highestRatedUser);
        return highestRatedUser;
    }
}
