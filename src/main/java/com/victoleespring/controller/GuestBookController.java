package com.victoleespring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.victoleespring.repository.GuestBookDAO;
import com.victoleespring.vo.GuestBookVO;

@Controller
@RequestMapping("/guestbook")
public class GuestBookController {
	@Autowired
	private GuestBookDAO guestBookDAO;
	
	@RequestMapping("/list")
	public String list(Model model) {
		List<GuestBookVO> list = guestBookDAO.getList();
		model.addAttribute("list", list);
		return "/WEB-INF/views/guestbook/list.jsp";
	}
	
	@RequestMapping("/deleteform")
	public String deleteform() {
		return "/WEB-INF/views/guestbook/deleteform.jsp";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String add(@ModelAttribute GuestBookVO vo) {
		guestBookDAO.insert(vo);
		return "redirect:/guestbook/list";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(@RequestParam Integer no, @RequestParam String pwd) {
		String dbpwd = guestBookDAO.getPwd(no);
		String parseInputPwd = guestBookDAO.getInputPwd(pwd);
		
		if(dbpwd.equals(parseInputPwd)) {
			guestBookDAO.delete(no);
		}
		
		return "redirect:/guestbook/list";
	}
}
