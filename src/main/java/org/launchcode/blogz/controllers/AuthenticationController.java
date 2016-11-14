package org.launchcode.blogz.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.launchcode.blogz.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AuthenticationController extends AbstractController {
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signupForm() {
		return "signup";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(HttpServletRequest request, Model model) {
		
		// TODO - implement signup
		
		// needs to make sure username and password are acceptable
		// needs to ensure password and verify are the same
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String verify = request.getParameter("verify");
		
		// Initial parameters
		boolean validUsername = User.isValidUsername(username);
		boolean validPassword = User.isValidPassword(password);
		boolean validMatch = password.equals(verify);
		
		if (validUsername == false) {
			model.addAttribute("username_error","This username is not allowed");
			return "signup"; // sends the user back to sign up page
		}
		
		if (validPassword == false) {
			model.addAttribute("password_error", "This password is not allowed");
			return "signup";
		}
		
		if (validMatch == false) {
			model.addAttribute("verify_error", "You did not re-type the passwords correctly");
			return "signup";
		}
		
		// Session thisSession = request.getSession();
		// This command doesn't work.
		
		// Assuming the user managed to go through all these without being "false"
		// Then, we need to add the information of the user's username and password into the session
		
		User user = new User(username, password);
		userDao.save(user); // allowed under CrudRepository
		
		// saving the new session
		HttpSession newSession = request.getSession(); // true parameter is not necessary
		setUserInSession(newSession, user);
		
		return "redirect:blog/newpost";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginForm() {
		return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, Model model) {
		
		// TODO - implement login
		
		return "redirect:blog/newpost";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request){
        request.getSession().invalidate();
		return "redirect:/";
	}
}
