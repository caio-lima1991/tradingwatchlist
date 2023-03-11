package com.trading.watchlist.tradingwatchlist.service;

import com.trading.watchlist.tradingwatchlist.domain.User;
import com.trading.watchlist.tradingwatchlist.dto.UserDTO;

public interface UserService {
	void saveUser(UserDTO userDTO);
	User findByEmail(String email);
}
