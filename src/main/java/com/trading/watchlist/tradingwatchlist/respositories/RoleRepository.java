package com.trading.watchlist.tradingwatchlist.respositories;

import com.trading.watchlist.tradingwatchlist.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);

}
