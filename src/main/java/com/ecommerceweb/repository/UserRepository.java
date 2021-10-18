package com.ecommerceweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerceweb.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long > {
	
	@Query("select u from User u where u.email =:email")
	public User getUserByEmail(@Param("email") String email);
	
	@Query("from User where id =:id")
	User findUserById(@Param("id") Long id);

}
