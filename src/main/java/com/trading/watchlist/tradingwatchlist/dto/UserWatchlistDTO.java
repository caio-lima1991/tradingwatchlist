package com.trading.watchlist.tradingwatchlist.dto;

import lombok.Data;

@Data
public class UserWatchlistDTO {

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
	private String mensagem;
}
