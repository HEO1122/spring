package com.increpas.home;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Test01 {

	@RequestMapping(path="/test.tst")
	public String getTest() {
		
		return "test01/Test01";
	}
	@RequestMapping("/test02/kieun.tst")
	public void getKieun() {
		System.out.println("*********************김기은 요청***********");
	}
	@RequestMapping("/test02/test02.cls")
	public ModelAndView getMyName(ModelAndView mv) {
		//ModelAndView에 데이터를 넣는 방법
		mv.addObject("DATA","허수경");
		//ModelAndView에 뷰를 넣는 방법은
		mv.setViewName("test02/test03"); // 뷰를 포워드방식으로 부르는 방법
		return mv;
	}
}
