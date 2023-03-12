package com.trading.watchlist.tradingwatchlist.respositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trading.watchlist.tradingwatchlist.domain.UserWatchlist;

import jakarta.transaction.Transactional;

public interface UserWatchlistRepository extends JpaRepository<UserWatchlist, Long>  {
	
	@Query("SELECT user FROM UserWatchlist user WHERE user.symbol = :symbol and user.username = :username")
	UserWatchlist findBySymbol(@Param("symbol") String symbol, @Param("username") String username);
	
	@Query("SELECT user FROM UserWatchlist user WHERE user.username = :username")
	List<UserWatchlist> retornarTickers(@Param("username") String username);
	
	@Modifying
	@Transactional
	@Query("DELETE FROM UserWatchlist user WHERE user.symbol = :symbol and user.username = :username")
	void deleteBySymbol(@Param("symbol")String symbol, @Param("username") String username);
}
