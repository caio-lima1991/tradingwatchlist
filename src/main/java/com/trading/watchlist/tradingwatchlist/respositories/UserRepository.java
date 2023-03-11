package com.trading.watchlist.tradingwatchlist.respositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.trading.watchlist.tradingwatchlist.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

}
