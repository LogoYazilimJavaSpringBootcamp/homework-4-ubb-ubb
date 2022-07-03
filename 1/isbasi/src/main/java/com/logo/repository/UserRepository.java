package com.logo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.logo.model.company.Customer;
import com.logo.model.User;

@Repository
public class UserRepository {

	private static List<User> userList = new ArrayList<>();

	public User save(User request) {

		userList.add(request);
		return request;
	}

	public List<User> findAll() {
		return userList;
	}

	public Optional<User> findByEmail(String email) {
		return userList.stream().filter(user -> user.getEmail().equals(email)).findFirst();
	}

}
