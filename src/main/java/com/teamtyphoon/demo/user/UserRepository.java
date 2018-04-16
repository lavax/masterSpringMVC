package com.teamtyphoon.demo.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.teamtyphoon.demo.error.EntityNotFoundException;

@Repository
public class UserRepository {
	private final Map<String, User> userMap = new ConcurrentHashMap<>();

	public User update(String email, User user) throws EntityNotFoundException {
		if (!exists(email)) {
			throw new EntityNotFoundException("User " + email + " cannot be found");
		}
		user.setEmail(email);
		return userMap.put(email, user);
	}

	public User save(User user) {
		return userMap.put(user.getEmail(), user);
	}

	public User findOne(String email) throws EntityNotFoundException {
		if (!exists(email)) {
			throw new EntityNotFoundException("User " + email + " cannot be found");
		}
		return userMap.get(email);
	}

	public List<User> findAll() {
		return new ArrayList<>(userMap.values());
	}

	public void delete(String email) throws EntityNotFoundException {
		if (!exists(email)) {
			throw new EntityNotFoundException("User " + email + "cannot be found");
		}
		userMap.remove(email);
	}

	public boolean exists(String email) {
		return userMap.containsKey(email);
	}

	public void reset(User... users) {
		userMap.clear();
		for (User user : users) {
			save(user);
		}
	}
}