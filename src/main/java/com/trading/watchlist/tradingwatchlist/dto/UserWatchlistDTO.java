package com.trading.watchlist.tradingwatchlist.dto;

import lombok.Data;

@Data
public class UserWatchlistDTO {

	private Long id;
	private String ticker;
	private Long amount;
	private Long buyingPrice;
	private UserDTO username;
}
