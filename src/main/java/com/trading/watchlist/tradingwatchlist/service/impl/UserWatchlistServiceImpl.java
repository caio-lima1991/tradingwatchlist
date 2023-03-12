package com.trading.watchlist.tradingwatchlist.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.trading.watchlist.tradingwatchlist.domain.UserWatchlist;
import com.trading.watchlist.tradingwatchlist.dto.UserWatchlistDTO;
import com.trading.watchlist.tradingwatchlist.respositories.UserWatchlistRepository;
import com.trading.watchlist.tradingwatchlist.service.UserWatchlistService;

import jakarta.persistence.EntityExistsException;

@Service
public class UserWatchlistServiceImpl implements UserWatchlistService {

	@Autowired
	private UserWatchlistRepository userWatchlistRepository;
	
	@Override
	public UserWatchlistDTO saveTicker(UserWatchlistDTO userWatchlistDTO) {
		UserWatchlist userWatchlist = new UserWatchlist();
		buscarCotacaoAntesdeSalvar(userWatchlistDTO);
		
		userWatchlist.setSymbol(userWatchlistDTO.getSymbol());
		userWatchlist.setUsername(userWatchlistDTO.getUsername());
		userWatchlist.setChange(userWatchlistDTO.getChange());
		userWatchlist.setChangePercent(userWatchlistDTO.getChangePercent());
		userWatchlist.setHigh(userWatchlistDTO.getHigh());
		userWatchlist.setLatesTradingDay(userWatchlistDTO.getLatesTradingDay());
		userWatchlist.setLow(userWatchlistDTO.getLow());
		userWatchlist.setOpen(userWatchlistDTO.getOpen());
		userWatchlist.setPreviousClose(userWatchlistDTO.getPreviousClose());
		userWatchlist.setPrice(userWatchlistDTO.getPrice());
		userWatchlist.setSymbol(userWatchlistDTO.getSymbol());
		userWatchlist.setVolume(userWatchlistDTO.getVolume());
		
		if(userWatchlistDTO.getMensagem() == null) {
			if(userWatchlistRepository.findBySymbol(userWatchlistDTO.getSymbol(), userWatchlistDTO.getUsername()) == null) {
				userWatchlistRepository.save(userWatchlist);
			}else {
				userWatchlistDTO.setMensagem("Ativo já está cadastrado");
			}	
		}
		
		return userWatchlistDTO;
	}

	@Override
	public List<UserWatchlistDTO> retornarTickers(String username) {
		List<UserWatchlistDTO> userWatchlistDTO = new ArrayList<>();
		List<UserWatchlist> userWatchlist = userWatchlistRepository.retornarTickers(username);
		
		if(!userWatchlist.isEmpty()) {
			for(UserWatchlist x: userWatchlist) {
				UserWatchlistDTO userWatchlistDTOTemp = new UserWatchlistDTO();
				
				userWatchlistDTOTemp.setSymbol(x.getSymbol());
				userWatchlistDTOTemp.setUsername(x.getUsername());
				userWatchlistDTOTemp.setId(x.getId());
				userWatchlistDTOTemp.setChange(x.getChange());
				userWatchlistDTOTemp.setChangePercent(x.getChangePercent());
				userWatchlistDTOTemp.setHigh(x.getHigh());
				userWatchlistDTOTemp.setLatesTradingDay(x.getLatesTradingDay());
				userWatchlistDTOTemp.setLow(x.getLow());
				userWatchlistDTOTemp.setOpen(x.getOpen());
				userWatchlistDTOTemp.setPreviousClose(x.getPreviousClose());
				userWatchlistDTOTemp.setPrice(x.getPrice());
				userWatchlistDTOTemp.setSymbol(x.getSymbol());
				userWatchlistDTOTemp.setVolume(x.getVolume());
						
				userWatchlistDTO.add(userWatchlistDTOTemp);
			} 
		}
		
		return userWatchlistDTO;
	}
	
	@Override
	public void deletarTickerSelecionado(UserWatchlistDTO userWatchlistDTO) {
		userWatchlistRepository.deleteBySymbol(userWatchlistDTO.getSymbol(), userWatchlistDTO.getUsername());
	}
	
	@Override
	public void atualizarTickerSelecionado(UserWatchlistDTO userWatchlistDTO) {
		UserWatchlist userWatchlist = new UserWatchlist();
		buscarCotacaoAntesdeSalvar(userWatchlistDTO);
		
		userWatchlist.setSymbol(userWatchlistDTO.getSymbol());
		userWatchlist.setUsername(userWatchlistDTO.getUsername());
		userWatchlist.setChange(userWatchlistDTO.getChange());
		userWatchlist.setChangePercent(userWatchlistDTO.getChangePercent());
		userWatchlist.setHigh(userWatchlistDTO.getHigh());
		userWatchlist.setLatesTradingDay(userWatchlistDTO.getLatesTradingDay());
		userWatchlist.setLow(userWatchlistDTO.getLow());
		userWatchlist.setOpen(userWatchlistDTO.getOpen());
		userWatchlist.setPreviousClose(userWatchlistDTO.getPreviousClose());
		userWatchlist.setPrice(userWatchlistDTO.getPrice());
		userWatchlist.setSymbol(userWatchlistDTO.getSymbol());
		userWatchlist.setVolume(userWatchlistDTO.getVolume());
		
		userWatchlistRepository.deleteBySymbol(userWatchlistDTO.getSymbol(), userWatchlistDTO.getUsername());
		userWatchlistRepository.save(userWatchlist);
	}

	private UserWatchlistDTO buscarCotacaoAntesdeSalvar(UserWatchlistDTO userWatchlistDTO) {
		RestTemplate restTemplate = new RestTemplate();
		
		String uri = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=" 
						+ userWatchlistDTO.getSymbol().toString() 
						+ "&apikey=C4FVKFD0YFBXD1X7";
		
		JSONObject response = new JSONObject(restTemplate.getForObject(uri, String.class));
		try{
			JSONObject globalQuote = response.getJSONObject("Global Quote");

			try{
				userWatchlistDTO.setSymbol(globalQuote.getString("01. symbol"));
				userWatchlistDTO.setChange(globalQuote.getString("09. change"));
				userWatchlistDTO.setChangePercent(globalQuote.getString("10. change percent"));
				userWatchlistDTO.setHigh(globalQuote.getString("03. high"));
				userWatchlistDTO.setLatesTradingDay(globalQuote.getString("07. latest trading day"));
				userWatchlistDTO.setLow(globalQuote.getString("04. low"));
				userWatchlistDTO.setOpen(globalQuote.getString("02. open"));
				userWatchlistDTO.setPreviousClose(globalQuote.getString("08. previous close"));
				userWatchlistDTO.setPrice(globalQuote.getString("05. price"));
				userWatchlistDTO.setVolume(globalQuote.getString("06. volume"));
			} catch(JSONException e) {
				userWatchlistDTO.setMensagem("Ativo não encontrado");
			}
		} catch(JSONException e) {
			String note = response.get("Note").toString();
			userWatchlistDTO.setMensagem(note);
		}
		
		return userWatchlistDTO;
	}
}
