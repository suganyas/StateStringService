package com.statestringservice.repository;

import com.statestringservice.model.User;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface UserDAO extends CrudRepository<User, String> {

    List<User> findByUserId(String userId);

}
