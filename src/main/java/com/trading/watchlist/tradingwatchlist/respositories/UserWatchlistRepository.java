package com.trading.watchlist.tradingwatchlist.respositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.trading.watchlist.tradingwatchlist.domain.UserWatchlist;

public interface UserWatchlistRepository extends JpaRepository<UserWatchlist, Long>  {

}
