package org.crazymages.datingdemoapp.service.match.impl;

import org.crazymages.datingdemoapp.entity.Gender;
import org.crazymages.datingdemoapp.entity.User;
import org.crazymages.datingdemoapp.service.database.UsersDatabaseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RandomMatchServiceTest {

    @Mock
    private UsersDatabaseService usersDatabaseService;

    @InjectMocks
    private RandomMatchService randomMatchService;

    List<User> users;


    @BeforeEach
    void init () {
        users = new ArrayList<>();
    }

    @Test
    void getNewMatch_returnsSameUserWhenUsersListHasOneElement_success() {
        // given
        User expected = new User(1, "John", Gender.MALE, 100);
        users.add(expected);

        when(usersDatabaseService.getUsersList()).thenReturn(users);

        // when
        User actual = randomMatchService.getNewMatch();

        // then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void getNewMatch_throwsExceptionWhenUsersListIsEmpty() {
        when(usersDatabaseService.getUsersList()).thenReturn(users);

        assertThrows(IllegalArgumentException.class, () -> randomMatchService.getNewMatch());
    }

    @Test
    void getNewMatch_throwsExceptionWhenUsersListIsNull() {
        when(usersDatabaseService.getUsersList()).thenReturn(null);

        assertThrows(NullPointerException.class, () -> randomMatchService.getNewMatch());
    }

}