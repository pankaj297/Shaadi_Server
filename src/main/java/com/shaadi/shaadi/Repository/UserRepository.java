package com.shaadi.shaadi.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.shaadi.shaadi.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByGender(String gender);
}
