package com.victoleespring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.victoleespring.repository.GuestBookDAO;

@Controller
public class GuestBookController {
	@Autowired
	private GuestBookDAO guestBookDAO;
	
	@RequestMapping("/main")
	public String main() {
		return "/WEB-INF/views/index.jsp";
	}
}
