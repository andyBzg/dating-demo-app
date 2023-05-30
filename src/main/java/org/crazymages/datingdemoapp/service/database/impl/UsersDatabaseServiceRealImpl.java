package org.crazymages.datingdemoapp.service.database.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crazymages.datingdemoapp.entity.Gender;
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

@Slf4j
@Service
@Primary
@RequiredArgsConstructor
public class UsersDatabaseServiceRealImpl implements UsersDatabaseService {

    private final UserRepository userRepository;

    @PersistenceContext
    private final EntityManager entityManager;


    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "usersList")
    public List<User> getUsersList() {
        log.info("Getting list of users");
        return userRepository.findAll();
    }

    @Override
    @Transactional()
    @CacheEvict(value = "usersList", allEntries = true)
    public void add(User user) {
        userRepository.save(user);
        log.info("Saved to DB: {}", user);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"usersList", "usersCache"}, allEntries = true)
    public void deleteByName(String name) {
        userRepository.deleteUserByName(name);
        log.info("Deleted from DB: {}", name);
    }

    @Override
    @Cacheable(value = "usersCache", key = "#id")
    public User getUserById(Integer id) throws InterruptedException {
        log.info("Getting user by id: {}", id);
        Thread.sleep(3000);
        Optional<User> optionalUser = userRepository.findById(id);
        log.info("Found user: {}", optionalUser);
        return optionalUser.orElse(null);
    }

    @Override
    @Transactional
    @CachePut(value = "usersCache", key = "#id")
    @CacheEvict(value = "usersList", allEntries = true)
    public User updateUser(Integer id, User user) {
        log.info("Getting user by id: {}", id);
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User modified = optionalUser.get();
            modified.setName(user.getName());
            modified.setGender(user.getGender());
            modified.setRating(user.getRating());
            userRepository.save(modified);
        }
        log.info("Updated user: {}", optionalUser);
        return optionalUser.orElse(null);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"usersList", "usersCache"}, allEntries = true)
    public void transferPoints(Integer fromId, Integer toId) {
        User donor = userRepository.getUserById(fromId);
        User recipient = userRepository.getUserById(toId);
//        Random random = new Random();

        donor.setRating(donor.getRating() - 100);
        userRepository.save(donor);

        /*if (random.nextInt(0, 10) < 9) {
            throw new RuntimeException();
        }*/

        recipient.setRating(recipient.getRating() + 100);
        userRepository.save(recipient);
        log.info("Transfer of points from user={} to user={} is successful", fromId, toId);
    }

    @Override
    @Transactional
    public void addTwoUsers() {
        User firstUser = new User();
        firstUser.setName("Mrs. First");
        firstUser.setGender(Gender.FEMALE);
        firstUser.setRating(100);

        User secondUser = new User();
        secondUser.setName("Mr. Second");
        secondUser.setGender(Gender.MALE);
        secondUser.setRating(200);

        entityManager.persist(firstUser);
        entityManager.persist(secondUser);
    }

}
