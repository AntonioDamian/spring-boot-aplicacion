package com.damian.springbootApp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.damian.springbootApp.entity.User;
import com.damian.springbootApp.repository.RoleRepository;
import com.damian.springbootApp.service.UserService;

@Controller
public class UserController {

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired 
	UserService userService;
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
/*	@GetMapping("/userForm")
	public String getUserForm() {
		return "user-form/user-view";
	}	*/
	
	@GetMapping("/userForm")
	public String getUserForm(Model model) {
		model.addAttribute("userForm", new User());
		model.addAttribute("roles",roleRepository.findAll());
		model.addAttribute("userList", userService.getAllUsers());
		model.addAttribute("listTab","active");
		return "user-form/user-view";
	}
	
	@PostMapping("/userForm")
	public String postUserForm(@Valid @ModelAttribute("userForm")User user, BindingResult result, ModelMap model) {
			if(result.hasErrors()) {
				model.addAttribute("userForm", user);
				model.addAttribute("formTab","active");
			}
			else {
				try {
					userService.createUser(user);
					model.addAttribute("userForm", new User());
					model.addAttribute("listTab","active");

				} catch (Exception e) {
					model.addAttribute("formErrorMessage",e.getMessage());
					model.addAttribute("userForm", user);
					model.addAttribute("formTab","active");
					model.addAttribute("userList", userService.getAllUsers());
					model.addAttribute("roles",roleRepository.findAll());
				}
			}

			model.addAttribute("userList", userService.getAllUsers());
			model.addAttribute("roles",roleRepository.findAll());
			return "user-form/user-view";
		}
}
