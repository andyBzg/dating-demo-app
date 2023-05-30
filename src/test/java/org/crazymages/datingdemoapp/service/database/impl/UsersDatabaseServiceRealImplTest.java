package org.crazymages.datingdemoapp.service.database.impl;

import org.crazymages.datingdemoapp.entity.Gender;
import org.crazymages.datingdemoapp.entity.User;
import org.crazymages.datingdemoapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsersDatabaseServiceRealImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UsersDatabaseServiceRealImpl usersDatabaseService;

    User user1;
    User user2;

    @BeforeEach
    void init() {
        user1 = new User(1, "Alice", Gender.FEMALE, 200);
        user2 = new User(2, "Carl", Gender.MALE, 100);
    }

    @Test
    void getUsersList_returnUsersFromUserRepository_success() {
        // given
        List<User> expected = List.of(user1, user2);
        when(userRepository.findAll()).thenReturn(expected);

        // when
        List<User> actual = usersDatabaseService.getUsersList();

        // then
        assertEquals(expected, actual);
    }


    @Test
    void add_saveUserToUserRepository_success() {
        // given

        // when
        usersDatabaseService.add(user1);

        // then
        verify(userRepository).save(user1);
    }


    @Test
    void deleteByName_deleteUserFromUserRepository_success() {
        // given
        String name = "Alice";

        // when
        usersDatabaseService.deleteByName(name);

        // then
        verify(userRepository).deleteUserByName(name);
    }


    @Test
    void getUserById_returnUserFromUserRepository_success() throws InterruptedException {
        // given
        Integer id = 1;
        when(userRepository.findById(id)).thenReturn(Optional.of(user1));

        // when
        User result = usersDatabaseService.getUserById(id);

        // then
        assertEquals(user1, result);
    }

    @Test
    void getUserById_returnNullIfUserDoesNotExist_success() throws InterruptedException {
        // given
        Integer id = 1;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        // when
        User actual = usersDatabaseService.getUserById(id);

        // then
        assertNull(actual);
    }


    @Test
    void updateUser_updateUserInUserRepositoryIfPresent_success() {
        // given
        Integer id = 1;
        User expected = user2;
        expected.setId(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(user1));

        // when
        User actual = usersDatabaseService.updateUser(id, expected);

        // then
        assertEquals(expected, actual);
        verify(userRepository).save(expected);
    }

    @Test
    void updateUser_returnNullIfUserDoesNotExist_ok() {
        // given
        Integer id = 1;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        // when
        User actual = usersDatabaseService.updateUser(id, user2);

        // then
        assertNull(actual);
        verify(userRepository, never()).save(any());
    }

    @Test
    void transferPoints_transfersPointsFromFirstUserToSecondUser_success() {
        // given
        Integer fromId = 1;
        Integer toId = 2;
        User firstUser = user1;
        User secondUser = user2;
        int expected1 = user1.getRating() - 100;
        int expected2 = user2.getRating() + 100;
        when(userRepository.getUserById(fromId)).thenReturn(firstUser);
        when(userRepository.getUserById(toId)).thenReturn(secondUser);

        // when
        usersDatabaseService.transferPoints(fromId, toId);

        // then
        assertEquals(expected1, firstUser.getRating());
        assertEquals(expected2, secondUser.getRating());
        verify(userRepository).save(firstUser);
        verify(userRepository).save(secondUser);
    }
}