package com.trading.watchlist.tradingwatchlist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;

import com.trading.watchlist.tradingwatchlist.domain.User;
import com.trading.watchlist.tradingwatchlist.dto.UserDTO;
import com.trading.watchlist.tradingwatchlist.service.UserService;

@Controller
public class AuthController {
	
	@Autowired
	private UserService userService;
	
    public AuthController(UserService userService) {
        this.userService = userService;
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
}
