package com.example.demo.repositories;

import com.example.demo.beans.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
	public User findByEmail(String email);
}
