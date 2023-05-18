package org.crazymages.datingdemoapp.service.database;

import org.crazymages.datingdemoapp.entity.User;

import java.util.List;

public interface UsersDatabaseService {

    List<User> getUsersList();

    void add(User user);

    void deleteByName(String name);

    User getUserById(Integer id) throws InterruptedException;

    User updateUser(Integer id, User user);

    void transferPoints(Integer fromId, Integer toId);
}
