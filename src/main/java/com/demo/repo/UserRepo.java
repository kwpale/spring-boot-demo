package com.demo.repo;

import com.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {

    @Query("select u from User u where u.username like %?1%")
    List<User> findAllByUserName(String username);

    @Query("select u from User u where u.username= :un")
    User findOneByUsername(@Param("un") String username);

    @Query("select u.username from User u where u.id= :id")
    String findUsernameById(Long id);

    @Query("select count(u) from User u")
    long countUsers();
}
