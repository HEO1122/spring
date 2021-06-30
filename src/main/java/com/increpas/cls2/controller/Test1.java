package com.increpas.cls2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

public class Test1 {
	@RequestMapping("/test02/test03.cls")
		public ModelAndView getMyName(ModelAndView mv) {
			//ModelAndView에 데이터를 넣는 방법
			mv.addObject("DATA","허수경");
			//ModelAndView에 뷰를 넣는 방법은
			mv.setViewName("test02/test03"); // 뷰를 포워드방식으로 부르는 방법
			return mv;
		}


}

