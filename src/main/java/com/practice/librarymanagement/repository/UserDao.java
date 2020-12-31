package com.practice.librarymanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.practice.librarymanagement.model.User;
import com.practice.librarymanagement.model.UserRole;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

	List<User> findByName(String name);
	Optional<User> findByEnrollmentId(long enrollmentId);
	Optional<User> findByEnrollmentIdAndPassword(long enrollmentId, String password);
	Optional<User> findByNameAndPassword(String name, String password);
	List<User> findByCategory(UserRole category);
	void deleteByEnrollmentId(long enrollmentId);
	
	//custom
	@Query("SELECT u FROM user u where u.name like ?1")		//?1 means 1st parameter of method
	List<User> findUsersLike(String pattern);
	
	
}
