package com.increpas.cls2.controller;

import java.util.*;

import javax.servlet.http.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;
import org.springframework.web.servlet.view.RedirectView;

import com.increpas.cls2.dao.*;
import com.increpas.cls2.service.SurveyService;
import com.increpas.cls2.vo.*;

/**
 * 이 클래스는 설문조사 관련 요청을 전담 처리할 클래스
 * @author 허수경
 * @since 2021.06.01
 * @version v.1.0
 * @see 
 * 			작업이력]
 * 				2021/06/01 - 담당자 : 허수경
 * 							작업내용 : 클래스 제작
 * 										설문조사 리스트 요청 함수 제작
 *
 */
@Controller
@RequestMapping("/survey")
public class Survey {
	@Autowired
	SurveyDao sDao;
	@Autowired
	SurveyService sSrvc;
	/*
	 * 진행중인 설문 리스트 요청 처리함수
	 */
	@RequestMapping("/surveyList.cls")
	public ModelAndView SurveyList(ModelAndView mv) {
		//데이터베이스 조회
		List list = sDao.getList();
		//데이터 전달하고
		mv.addObject("LIST", list);
		//뷰 설정
		mv.setViewName("survey/surveyList");
		return mv;
	}
	/*
	 * 설문조사 페이지 요청 처리함수
	 */
	@RequestMapping("/survey.cls")
	public ModelAndView surveyDetail(SurveyVO sVO, ModelAndView mv, HttpSession session, RedirectView rv) {
		//할일
		//문항리스트 꺼내고
		System.out.println("서베이.cls");
		ArrayList<SurveyVO> list = (ArrayList<SurveyVO>) sDao.questList(sVO.getSino());
		//문항에 해당하는 보기 리스트 꺼내서 채워주고
		for(SurveyVO s : list) {
			int qno = s.getQno();
			ArrayList<SurveyVO> l = (ArrayList<SurveyVO>) sDao.exList(qno);
			s.setList(l);
		}
		//데이터 전달하고
		mv.addObject("TITLE", sVO.getTitle());
		mv.addObject("LIST", list);
		mv.addObject("LEN", list.size());
		
		//뷰 
		mv.setViewName("survey/survey");
		return mv;
	}
	
	@RequestMapping("/surveyProc.cls")
	public ModelAndView surveyProc(SurveyVO sVO, ModelAndView mv, RedirectView rv, HttpSession session) {
		try {
			sSrvc.addSrvyService(sVO, rv, session);
		}catch(Exception e) {
			rv.setUrl("/cls2/survey/survey.cls");
			e.printStackTrace();
		}
		mv.setView(rv);
		return mv;
	}
}
