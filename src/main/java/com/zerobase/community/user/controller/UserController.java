package com.zerobase.community.user.controller;


import com.zerobase.community.user.model.UserInput;
import com.zerobase.community.user.service.UserService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequiredArgsConstructor
@Controller
public class UserController {

	@Value("${adminCode}")
	private String adminCode;

	@RequestMapping("/")
	String index(Model model) {

		return "index";
	}

	private final UserService userService;
	@RequestMapping("/user/login")
	public String login() {

		return "user/login";
	}

	@GetMapping("/user/register")
	public String register() {

		return "user/register";
	}

	@GetMapping("/user/register_admin")
	public String registerAdmin(Model model) {

		model.addAttribute("adminCode", adminCode);

		return "user/register_admin";
	}

	@RequestMapping(value = {"/user/register", "/user/register_admin"}, method = RequestMethod.POST)
	public String registerSubmit(Model model, HttpServletRequest request
		, UserInput parameter) {

		boolean result = userService.register(parameter);
		model.addAttribute("result", result);

		return "user/register_complete";
	}




}
