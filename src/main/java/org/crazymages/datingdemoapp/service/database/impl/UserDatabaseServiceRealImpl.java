package org.crazymages.datingdemoapp.service.database.impl;

import org.crazymages.datingdemoapp.entity.User;
import org.crazymages.datingdemoapp.repository.UserRepository;
import org.crazymages.datingdemoapp.service.database.UsersDatabaseService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class UserDatabaseServiceRealImpl implements UsersDatabaseService {

    private final UserRepository userRepository;

    public UserDatabaseServiceRealImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUsersList() {
        return userRepository.findAll();
    }

    @Override
    public void add(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteByName(String name) {
        userRepository.deleteUserByName(name);
    }

}
