package com.trading.watchlist.tradingwatchlist.service;

import java.util.List;

import com.trading.watchlist.tradingwatchlist.dto.UserWatchlistDTO;

public interface UserWatchlistService {
	UserWatchlistDTO saveTicker(UserWatchlistDTO userWatchlistDTO);
	List<UserWatchlistDTO> retornarTickers(String username);
	void deletarTickerSelecionado(UserWatchlistDTO userWatchlistDTO);
	void atualizarTickerSelecionado(UserWatchlistDTO userWatchlistDTO);
}
