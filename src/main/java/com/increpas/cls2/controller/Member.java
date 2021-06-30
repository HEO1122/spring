package com.increpas.cls2.controller;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.*;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;
import org.springframework.web.servlet.view.*;

import com.increpas.cls2.dao.*;
import com.increpas.cls2.vo.*;

import oracle.net.aso.e;

/**
 * 이 클래스는 스프링 수업으 회원관련 요청처리를 
 * @author class02
 *
 */
@Controller // 이 클래스가 요청을 처리할 컨트롤러의 역할을 할 컨트롤러 클래스로 
// 만들어주는  어노테이션
@RequestMapping("/member") // 이 클래스의 참수를 요청할 때 공통적으로 회원관련된 요청을 할 것이고그때마다 아ㅠ에 붙여줄 경로는 컨트롤러에서 공통적으로 처리하기로 하자
public class Member {
	@Autowired
	MemberDao mDao;
	
	@RequestMapping("/memberList.cls")
	public ModelAndView memberList(ModelAndView mv) {
		//데이터베이스에서 리스트 조회하고
		List list = mDao.getMembList();
		//리스트 뷰에 심고
		mv.addObject("LIST", list);
		/*
		 * spring 에서 뷰에 데이터를전달 하는 방법
		 * addObject("키값", 데이터);
		 * => req.setAttribute("키값", 데이터);
		 * 와 동일한 역할을 하는 함수
		 */
		mv.setViewName("member/memberList");
		return mv;
	}
	
	@RequestMapping(value="/memberInfo.cls", params="mno", method=RequestMethod.POST)
	public ModelAndView memberInfo(ModelAndView mv, int mno) {
		//데이터 베이스 조회
		MemberVO mVO = mDao.memberInfo(mno);
		
		//데이터 뷰에 전달하고
		mv.addObject("DATA", mVO);
		mv.setViewName("member/memberInfo");
		return mv;
	}
	@RequestMapping("/login.cls")
	public ModelAndView getLogin(HttpSession session, ModelAndView mv, RedirectView rv) {
		if(isLogin(session)) {
			//이 경우는 이미 로그인이 되어있는 경우이고
			// 로그인 페이지를 부르면 안되는 경우이다.
			//따라서 메인페이지로 돌려보낸다.
			
			/*
			 * 참고]
			 *  스프링에서 Redirect 방식으로 뷰를 처리하는 방법
			 *  1.	
			 *  	RedirectView 객체에 
			 *  	rv.setUrl("요청주소")
			 *  2.
			 *  	ModelAndView에 
			 *  	mv.setView(rv);
			 *  
			 */
			rv.setUrl("/cls2/");
			mv.setView(rv);
		}else {
			String view = "member/login";
			mv.setViewName(view);
		}
		String view ="member/login";
		
		return mv;
		
	}


	@RequestMapping("/loginProc.cls")
	public ModelAndView loginProc(/* String id, String pw, */ MemberVO mVO, ModelAndView mv,
																HttpSession session, RedirectView rv) {
		String view ="/cls2/";
		if(!isLogin(session)) {
			
		
		/*
		 * System.out.println("id:"+id); 
		 * System.out.println("pw:"+pw);
		
		System.out.println("id:"+mVO.getId());
		System.out.println("pw:"+mVO.getPw());
		System.out.println("ano:"+mVO.getAno());
		System.out.println(mVO);
		 */
			int cnt = mDao.getLogin(mVO);
			mVO.setCnt(cnt);
			if(cnt ==1) {
				session.setAttribute("SID", mVO.getId());
			}else {
				view = "/cls2/member/login.cls";
			}

		}
		rv.setUrl(view);
		mv.setView(rv);
		return mv;
		
	}
	@RequestMapping("/logout.cls")
	public ModelAndView logout(HttpSession session, ModelAndView mv, RedirectView rv) {
		session.removeAttribute("SID");
		rv.setUrl("/cls2/");
		mv.setView(rv);
		return mv;
	}
	/*
	 * 회원가입 아이디체크 요청 처리
	 */
	
	@RequestMapping(value="/idCheck.cls")
	@ResponseBody
	public HashMap<String, String> idCheck(String id) {
		int cnt = mDao.getIdCnt(id);
		System.out.println("**************idCheck cnt : " + cnt);
		/*
		 * jsp 프로젝트 에서는 외부 api를 사용하새 간단하게 비동기 통신의 결과 문서를
		 * 만들어 줄 수 도 있었지만 우리의 경우는
		 * 직접 응답 문서를 만들어주는 코드를 작성해서 응답했었다.
		 * 예]
		 *  	result라는 키값을 갖는 json데이터를 만들경우
		 *  
		 *  pw.println("{");
		 *  pw.println("\"result\""+결과값 변수+"\"");
		 *  pw.println("}");
		 *  
		 *  의 형태로 작업을 했었는데 이 작업이 상당히 불편하다.
		 *  자바의 객체를 비동기 통신 응답문서(json데이터)로 만들어주는 외부 api가 있는데
		 *  gson,sackson이 있다.
		 *  두가지 모두 자바 객체를 응답문서로 만들어주는 역할 하고있다.
		 *  스프링에서는 
		 *  jackson-core에서 이 응답문서를 만들어주는 기능을 편하게 제공해주고 있고
		 *  사용하는 방법은
		 *  	1. 처리함수에 @ResponseBody 어노테이션을 붙여서 처리
		 *  	2. 반환값에 사용할 변수 앞에 @ResponseBody라고 붙여주는 방법
		 *  
		 *  예]
		 *  	
		 *  1.
		 *  	@ResponseBody 
		 *  	public String getData(){
		 *  		String sid = "euns";
		 *  		return sid;
		 *  	}
		 *  => 응답문서내용
		 *  	euns
		 *  
		 *  2.
		 *  	@ResponseBody 
		 *  	public HashMap getData(){
		 *  		String sid = "euns";
		 *  		HashMap map = new HashMap();
		 *  		map.put("id",sid);
		 *  		return map;
		 *  	}
		 *		=> 응답 문서 내용
		 *		{
		 *			"id": "euns"
		 *		}
		 *  
		 */
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("result","NO");
		map.put("id", id);
		if(cnt != 1) {
			map.put("result","OK");
		}
		return map;
	}
	
	public boolean isLogin(HttpSession session) {
		String sid = (String) session.getAttribute("SID");
		
		return (sid == null) ? false : true; 
	}
	//내정보조회 요청 처리함수
	@RequestMapping("/myInfo.cls")
	public ModelAndView myInfo(ModelAndView mv, HttpSession session, RedirectView rv) {
		//할일
		//1. 로그인 검사
		if(isLogin(session)) {
			//2. 아이디 꺼내오기
			String sid =(String) session.getAttribute("SID");
			//3. 데이터베이스 작업하고
			MemberVO mVO = mDao.getMyInfo(sid);
			//4. 만들어진 데이터 뷰에 보내고
			mv.addObject("DATA", mVO);
			//5. 뷰를 정하고
			mv.setViewName("member/memberInfo"); //포워드 방식
		}else {
			//로그인 안 한경우
			//로그인 페이지로 보낸다.
			rv.setUrl("/cls2/member/login.cls"); //리다이렉트의 경우
			mv.setView(rv);
		}
		//6. 반환값 반환 하고
		return mv;
	}
	@RequestMapping("/join.cls")
	public ModelAndView joinForm(ModelAndView mv, HttpSession session, RedirectView rv) {
		if(isLogin(session)) {
			rv.setUrl("/cls2/");
			mv.setView(rv);
			return mv;
		}
		
		String view = "member/join";
		mv.setViewName(view);
		return mv;
	}
	@RequestMapping("/joinProc.cls")
	public ModelAndView joinProc(MemberVO mVO, ModelAndView mv, HttpSession session, RedirectView rv) {
		if(isLogin(session)) {
			rv.setUrl("/cls2/gBoard/gBoardWrite.cls");
			mv.setView(rv);
			return mv;
		}
		
		int cnt= mDao.addMember(mVO);
		if(cnt ==1) {
			session.setAttribute("SID", mVO.getId());
			System.out.println("############mno : " + mVO.getMno());
			rv.setUrl("/cls2/gBoard/gBoardWrite.cls");
		}else {
			rv.setUrl("/cls2/member/join.cls");
		}
		mv.setView(rv);
		return mv;
		
	}
	
	//내정보 수정 폼 보기요청 처리함수
	@RequestMapping("/myInfoEdit.cls")
	public ModelAndView memberEdit(ModelAndView mv, HttpSession session, RedirectView rv) {
		if(!isLogin(session)) {
			rv.setUrl("/cls2/member/login.cls");
			mv.setView(rv);
			return mv;
		}
		System.out.println("###########################");
		//아이디 꺼내고
		String sid = (String)session.getAttribute("SID");
		//데이터 베이스 조회하기
		MemberVO mVO = mDao.getMyInfo(sid);
		//내정보를 조회했으면 내 성별에 맞는 아바타 리스트를 조회한다.
		List list = mDao.avtGenList(mVO.getGen());
		
		//데이터 뷰에 심고
		mv.addObject("DATA", mVO);
		mv.addObject("LIST", list);
		
		//뷰 부르고
		mv.setViewName("member/myInfoEdit");
		//데이터 반환하고
		return mv;
	}
	
	//내정보 수정 처리요청 처리함수
	@RequestMapping("/myInfoEditProc.cls")
	public ModelAndView memberEditProc(MemberVO mVO, ModelAndView mv, HttpSession session, RedirectView rv) {
		if(!isLogin(session)) {
			rv.setUrl("/cls2/member/login.cls");
			mv.setView(rv);
			return mv;
		}
		
		int cnt = mDao.updateInfo(mVO);
		String view = "/cls2/member/myInfo.cls";
		if(cnt != 1) {
			view = "/cls2/member/myInfoEdit.cls";
		}
		rv.setUrl(view);
		mv.setView(rv);
		return mv;
	}
}
