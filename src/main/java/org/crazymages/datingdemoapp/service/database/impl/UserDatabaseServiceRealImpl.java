package org.crazymages.datingdemoapp.service.database.impl;

import org.crazymages.datingdemoapp.entity.User;
import org.crazymages.datingdemoapp.repository.UserRepository;
import org.crazymages.datingdemoapp.service.database.UsersDatabaseService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Primary
public class UserDatabaseServiceRealImpl implements UsersDatabaseService {

    private final UserRepository userRepository;

    public UserDatabaseServiceRealImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Cacheable(value = "usersList")
    public List<User> getUsersList() {
        return userRepository.findAll();
    }

    @Override
    @CacheEvict(value = "usersList", allEntries = true)
    public void add(User user) {
        userRepository.save(user);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"usersList", "usersCache"}, allEntries = true)
    public void deleteByName(String name) {
        userRepository.deleteUserByName(name);
    }

    @Override
    @Cacheable(value = "usersCache", key = "#id")
    public User getUserById(Integer id) throws InterruptedException {
        Thread.sleep(3000);
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }

    @Override
    @CachePut(value = "usersCache", key = "#id")
    @CacheEvict(value = "usersList", allEntries = true)
    public User updateUser(Integer id, User user) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User modified = userOptional.get();
            modified.setName(user.getName());
            modified.setGender(user.getGender());
            modified.setRating(user.getRating());
            userRepository.save(modified);
        }
        return userOptional.orElse(null);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"usersList", "usersCache"}, allEntries = true)
    public void transferPoints(Integer fromId, Integer toId) {
        User donor = userRepository.getUserById(fromId);
        User recipient = userRepository.getUserById(toId);
        Random random = new Random();

        donor.setRating(donor.getRating() - 100);
        userRepository.save(donor);

        if (random.nextInt(0, 10) < 9) {
            throw new RuntimeException();
        }

        recipient.setRating(recipient.getRating() + 100);
        userRepository.save(recipient);
    }

}
