package org.crazymages.datingdemoapp.repository;

import org.crazymages.datingdemoapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    void deleteUserByName(String name);

    User getUserById(Integer id);

    User getUserByName(String name);

    /*Collection<User> getUsersByIdAfterOrderByNameDesc(int id);
    //Найти всех пользователей, id которых "после" int id
    // и отсортировать их по имени в обратном порядке

    Collection<User> getUsersByIdIsIn(Collection<Integer> id);
    //SELECT * FROM users WHERE id IN (....)

    User getTopById(int id);

    void deleteUserByNameEndsWith(String endsWith);
    //DELETE FROM users WHERE name ilake '%.....'

    void updateUserById(Integer id);*/
}
