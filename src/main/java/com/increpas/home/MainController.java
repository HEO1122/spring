package com.increpas.home;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.increpas.home.dao.SurveyDao;

@Controller
public class MainController {
	@Autowired
	SurveyDao sDao;
	
	@RequestMapping(path="/", method=RequestMethod.GET)
	public ModelAndView getMain(ModelAndView mv) {
		
		int cnt = 0;
		cnt = sDao.getPcount();
		mv.addObject("SCOUNT", cnt);
		
		System.out.println("******");
		mv.setViewName("main");
		return mv;
	}
	
}
