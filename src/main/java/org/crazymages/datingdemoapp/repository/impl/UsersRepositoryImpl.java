package org.crazymages.datingdemoapp.repository.impl;

import jakarta.annotation.PostConstruct;
import org.crazymages.datingdemoapp.entity.Gender;
import org.crazymages.datingdemoapp.entity.User;
import org.crazymages.datingdemoapp.repository.UsersRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsersRepositoryImpl implements UsersRepository {

    List<User> userList;

    @PostConstruct
    public void init() {
        User user = new User();
        user.setName("Даниил");
        user.setGender(Gender.MALE);
        user.setRating(10);

        User user1 = new User();
        user1.setName("Андрей");
        user1.setGender(Gender.MALE);
        user1.setRating(5);

        User user2 = new User();
        user2.setName("Александр");
        user2.setGender(Gender.MALE);
        user2.setRating(9);

        userList = new ArrayList<>();
        userList.add(user);
        userList.add(user1);
        userList.add(user2);
    }

    @Override
    public List<User> getUsersList() {
        return userList;
    }
}
