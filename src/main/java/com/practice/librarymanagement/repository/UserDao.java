package com.practice.librarymanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
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
}
