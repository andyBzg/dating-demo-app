package org.crazymages.datingdemoapp.repository.impl;

import jakarta.annotation.PostConstruct;
import org.crazymages.datingdemoapp.entity.Gender;
import org.crazymages.datingdemoapp.entity.User;
import org.crazymages.datingdemoapp.repository.UsersRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
@Primary
public class UserRepositoryImprovedStubImpl implements UsersRepository {

    private List<User> userList;


    @PostConstruct
    public void init() {
        userList = new ArrayList<>();
        String filePath = "src/main/UsersDB.txt";
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] splittedString = line.split(", ");
                User user = new User();
                user.setName(splittedString[0]);
                user.setGender(Gender.valueOf(splittedString[1]));
                user.setRating(Integer.parseInt(splittedString[2]));
                userList.add(user);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getUsersList() {
        return userList;
    }
}
