package com.example.demo.services;

import com.example.demo.beans.Card;
import com.example.demo.beans.User;
import com.example.demo.repositories.CardRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.utils.CryptoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.message.AuthException;

@Service
public class UserServices {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CardRepository cardRepository;

	public User getUser(Long id) {
		if (id == null) {
			return null;
		}
		return userRepository.findById(id).orElse(null);
	}

	public Card addCardToUser(Card card, Long userId) {
		card.setUserId(userId);
		Card savedCard = cardRepository.save(card);
		return savedCard;
	}

	public User createUser(String email, String password, String firstName, String middleName, String lastName) throws AuthException {
		User user = userRepository.findByEmail(email);
		if (user != null) {
			throw new AuthException("User " + email + " already exists");
		}
		user = new User().setEmail(email)
				.setFirstName(firstName)
				.setMiddleName(middleName)
				.setLastName(lastName)
				.setPassword(CryptoUtils.encode(password));
		return userRepository.save(user);
	}

	public boolean validUser(String email, String password) throws AuthException {
		if (email == null || email.isEmpty()) {
			throw new AuthException("No userId specified");
		}
		if (password == null || password.isEmpty()) {
			throw new AuthException("No password specified");
		}
		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new AuthException("No user with the email " + email + " is registered");
		}

		if (!password.equals(CryptoUtils.matches(password, user.getEmail()))) {
			throw new AuthException("Invalid password");
		}
		return true;
	}
}
