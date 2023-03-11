package com.trading.watchlist.tradingwatchlist.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.trading.watchlist.tradingwatchlist.domain.User;
import com.trading.watchlist.tradingwatchlist.dto.UserDTO;
import com.trading.watchlist.tradingwatchlist.respositories.UserRepository;
import com.trading.watchlist.tradingwatchlist.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
	
	@Autowired
	private UserRepository userRepository;
	
    @Override
    public void saveUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());

        user.setPassword(PASSWORD_ENCODER.encode(userDTO.getPassword()));

        userRepository.save(user);
    }

	@Override
	public User findByEmail(String email) {
		User user = userRepository.findByEmail(email);
		return user;
	}
}
