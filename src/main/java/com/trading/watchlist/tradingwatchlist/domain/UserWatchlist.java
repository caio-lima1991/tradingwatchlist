package com.trading.watchlist.tradingwatchlist.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="userWatchlist")
public class UserWatchlist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String symbol;
	private String username;
	private String price;
	private String change;
	private String open;
	private String volume;
	private String latesTradingDay;
	private String previousClose;
	private String changePercent;
	private String high;
	private String low;
}
