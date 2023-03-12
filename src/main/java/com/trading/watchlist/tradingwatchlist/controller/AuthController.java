package com.trading.watchlist.tradingwatchlist.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;

import com.trading.watchlist.tradingwatchlist.domain.User;
import com.trading.watchlist.tradingwatchlist.domain.UserWatchlist;
import com.trading.watchlist.tradingwatchlist.dto.UserDTO;
import com.trading.watchlist.tradingwatchlist.dto.UserWatchlistDTO;
import com.trading.watchlist.tradingwatchlist.service.UserService;
import com.trading.watchlist.tradingwatchlist.service.UserWatchlistService;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
public class AuthController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private UserWatchlistService userWatchlistService;

	public AuthController(UserService userService, UserWatchlistService userWatchlistService) {
		super();
		this.userService = userService;
		this.userWatchlistService = userWatchlistService;
	}

	@GetMapping("/login")
    public String loginForm() {
        return "login";
    }
	
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        UserDTO user = new UserDTO();
        model.addAttribute("user", user);
        return "register";
    }
    
    @PostMapping("/register/save")
	public String registration(@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult result, Model model) {
    	User existingUser = userService.findByEmail(userDTO.getEmail());

		if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
			result.rejectValue("email", null, "Já existe uma conta com esse email");
		}

		if (result.hasErrors()) {
			model.addAttribute("user", userDTO);
			return "/register";
		}

		userService.saveUser(userDTO);
		return "redirect:/register?success";
	}
    
    @GetMapping("/hello")
    public String showTickersForm(Model model) {
    	
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	List<UserWatchlistDTO> userWatchlist = new ArrayList<>();
    	UserWatchlistDTO userWatchlistSave = new UserWatchlistDTO();
    	
    	if (!(auth instanceof AnonymousAuthenticationToken)) {
    	    String currentUserName = auth.getName();
    	    userWatchlist = userWatchlistService.retornarTickers(currentUserName);
    	}   	
        
    	model.addAttribute("userWatchlistSave", userWatchlistSave);
        model.addAttribute("watchlist", userWatchlist);
    	return "hello";
    }
    
    @PostMapping("/hello/save")
    public String salvarTicker(@Valid @ModelAttribute("userWatchlistSave") UserWatchlistDTO userWatchlistDTO, BindingResult result, Model model) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	userWatchlistDTO.setUsername(auth.getName());
    	
    	userWatchlistService.saveTicker(userWatchlistDTO);
    	if(!(userWatchlistDTO.getMensagem() == null)) {
    		result.rejectValue("symbol", null, userWatchlistDTO.getMensagem().toString());
    	}
    	
		if (result.hasErrors()) {
			model.addAttribute("userWatchlistSave", userWatchlistDTO);
			return "/hello";
		}
    	
    	return "redirect:/hello?success";
    }
    
    @PostMapping("/hello/delete")
    public String deletarTicker(@Valid @ModelAttribute("userWatchlist") UserWatchlistDTO userWatchlistDTO, BindingResult result, Model model) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	userWatchlistDTO.setUsername(auth.getName());
    	
    	userWatchlistService.deletarTickerSelecionado(userWatchlistDTO);
    	return "redirect:/hello?success";
    }
    
    @PostMapping("/hello/update")
    public String atualizarTicker(@Valid @ModelAttribute("userWatchlist") UserWatchlistDTO userWatchlistDTO, BindingResult result, Model model) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	userWatchlistDTO.setUsername(auth.getName());
    	
    	userWatchlistService.atualizarTickerSelecionado(userWatchlistDTO);
    	return "redirect:/hello?success";
    }
}
