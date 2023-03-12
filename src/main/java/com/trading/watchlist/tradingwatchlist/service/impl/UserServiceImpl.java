package com.trading.watchlist.tradingwatchlist.service.impl;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.trading.watchlist.tradingwatchlist.domain.Role;
import com.trading.watchlist.tradingwatchlist.domain.User;
import com.trading.watchlist.tradingwatchlist.dto.UserDTO;
import com.trading.watchlist.tradingwatchlist.respositories.RoleRepository;
import com.trading.watchlist.tradingwatchlist.respositories.UserRepository;
import com.trading.watchlist.tradingwatchlist.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
	
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
		super();
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	@Override
    public void saveUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());

        user.setPassword(PASSWORD_ENCODER.encode(userDTO.getPassword()));
        Role role = roleRepository.findByName("ROLE_ADMIN");
        if(role == null){
            role = checkRoleExist();
        }
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

	@Override
	public User findByEmail(String email) {
		User user = userRepository.findByEmail(email);
		return user;
	}
	
    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }
}
