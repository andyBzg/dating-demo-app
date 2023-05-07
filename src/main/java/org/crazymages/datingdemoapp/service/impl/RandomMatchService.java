package org.crazymages.datingdemoapp.service.impl;

import org.crazymages.datingdemoapp.entity.User;
import org.crazymages.datingdemoapp.repository.UsersRepository;
import org.crazymages.datingdemoapp.service.MatchService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@Primary
public class RandomMatchService implements MatchService {

    private UsersRepository usersRepository;

    public RandomMatchService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public User getNewMatch() {
        List<User> users = usersRepository.getUsersList();
        Random random = new Random();
        int i = random.nextInt(users.size());
        return users.get(i);
    }
}
