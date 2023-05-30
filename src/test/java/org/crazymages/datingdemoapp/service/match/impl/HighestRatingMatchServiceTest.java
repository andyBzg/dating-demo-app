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
class HighestRatingMatchServiceTest {

    @Mock
    private UsersDatabaseService usersDatabaseService;

    @InjectMocks
    private HighestRatingMatchService highestRatingMatchService;

    List<User> users;

    @BeforeEach
    void init() {
        users = new ArrayList<>();
    }


    @Test
    void getNewMatch_twoUserWithDifferentPoints_success() {
        //given
        User user = new User(1, "Daniil", Gender.MALE, 100);
        User expected = new User(2, "Kirill", Gender.MALE, 101);

        users.add(user);
        users.add(expected);

        when(usersDatabaseService.getUsersList()).thenReturn(users);


        //when
        User actual = highestRatingMatchService.getNewMatch();


        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void getNewMatch_twoUserWithSamePoints_returnsFirstFromList_success() {
        //given
        User user1 = new User(1, "John", Gender.MALE, 100);
        User user2 = new User(2, "Carl", Gender.MALE, 100);

        users.add(user1);
        users.add(user2);

        User expected = users.get(0);

        when(usersDatabaseService.getUsersList()).thenReturn(users);

        //when
        User actual = highestRatingMatchService.getNewMatch();

        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void getNewMatch_returnsNullWhenUsersListIsEmpty() {
        when(usersDatabaseService.getUsersList()).thenReturn(users);

        User actual = highestRatingMatchService.getNewMatch();

        assertNull(actual);
    }

    @Test
    void getNewMatch_throwsExceptionWhenUsersListIsNull() {
        when(usersDatabaseService.getUsersList()).thenReturn(null);

        assertThrows(NullPointerException.class, () -> highestRatingMatchService.getNewMatch());
    }
}