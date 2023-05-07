package org.crazymages.datingdemoapp.service.impl;

import org.crazymages.datingdemoapp.entity.User;
import org.crazymages.datingdemoapp.repository.UsersRepository;
import org.crazymages.datingdemoapp.service.MatchService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@Primary
public class HighestRatingMatchService implements MatchService {

    private UsersRepository usersRepository;

    public HighestRatingMatchService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public User getNewMatch() {
        List<User> users = usersRepository.getUsersList();
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
