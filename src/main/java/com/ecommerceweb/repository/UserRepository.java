package com.ecommerceweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerceweb.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long > {

}
