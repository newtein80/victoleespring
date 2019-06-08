package com.victoleespring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.victoleespring.repository.GuestBookDAO;

@Controller
@RequestMapping("/main")
public class GuestBookController {
	@Autowired
	private GuestBookDAO guestBookDAO;
	
	@RequestMapping(value="/", method=RequestMethod.GET) // 요청할 때는 http://localhost:8080/guestbook/main/ 와 같이 끝에 /를 붙여야 합니다.
	public String main(@RequestParam(value="a", required=true, defaultValue="one") String a) {
		System.out.println(a);
		return "/WEB-INF/views/index.jsp";
	}
	
	@RequestMapping(value="/two", method=RequestMethod.GET)
	public String main2(@RequestParam(value="a", required=true, defaultValue="two") String a) {
		System.out.println(a);
		return "/WEB-INF/views/index.jsp";
	}
	
	/*
	@RequestMapping(value="/{no}", method=RequestMethod.GET)
	public String main3(@PathVariable("no") Integer no) {
		System.out.println(no);
		return "/WEB-INF/views/index.jsp";
	}
	*/
	@RequestMapping(value="/{no:(?!assets|image|search).*}", method=RequestMethod.GET) // main/ 이후의 문자열 중 assets , image , search 문자열을 제외한 모든 값은 no 변수로 받는다 
	public String main4(@PathVariable("no") Integer no) {
		System.out.println(no);
		return "/WEB-INF/views/index.jsp";
	}
	
	@RequestMapping(value="/redirect")
	public String main5() {
		return "redirect:/main/";
	}
	
	@RequestMapping(value="/redirect2")
	public String main6() {
		return "redirect:/main/two?a=9999";
	}
}
