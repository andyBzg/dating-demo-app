package org.crazymages.datingdemoapp.service.match.impl;

import org.crazymages.datingdemoapp.entity.User;
import org.crazymages.datingdemoapp.service.database.UsersDatabaseService;
import org.crazymages.datingdemoapp.service.match.MatchService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@Primary
public class HighestRatingMatchService implements MatchService {

    private final UsersDatabaseService usersDatabaseService;

    public HighestRatingMatchService(UsersDatabaseService usersDatabaseService) {
        this.usersDatabaseService = usersDatabaseService;
    }

    @Override
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

        return highestRatedUser;
    }
}
