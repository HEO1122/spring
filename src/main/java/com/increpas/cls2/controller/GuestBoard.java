package com.increpas.cls2.controller;
import java.util.List;

/**
 * 이클래스는 게스트 게시판 관련 요청을 처리할 컨트롤러 클래스
 * @since 2021.05.18
 * @version v.1.0
 * @see 
 * 		작업이력 ]
 * 				2021.05.18 - 허수경
 * 				작업내용 : 클래스 생성
 */
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.increpas.cls2.dao.*;
import com.increpas.cls2.util.PageUtil;
import com.increpas.cls2.vo.*;

@Controller
@RequestMapping("/gBoard")
public class GuestBoard {

	@Autowired
	GBoardDao gDao;
	
	@RequestMapping("/gBoardList.cls")
	public ModelAndView gBoardList(HttpSession session, PageUtil page,ModelAndView mv) {
		/*
		 * 이 함수는 방명록 리스트 조회 페이지를 요청했을 때 
		 * 해당 페이지를 응답해주는 요청처리함수이다.
		 * 
		 * 이제까지는 회원관련 기능만 처리했어서
		 * 회원관련 sql, dao를 사용했었다.
		 * 
		 * 지금부터는 방명록 관련 요청처리를 해야하기 때문에
		 * 방명록, dao, sql 파일이 만들어져야 하고
		 * 이때 dao는 컨트롤러에서 자동의존주입을 해서 사용하기 때문에
		 * 빈처리가 되어야겠다.
		 * 
		 * 할일 ]
		 * 		dao bean처리, 마이바티스 별칭 만들고, 질의명령 추가, 
		 */
		//할일 
		
			
		int nowPage = page.getNowPage();
			if(nowPage ==0) {
				nowPage =1;
			}
			
		int total = gDao.getTotal();
		//페이지 유틸 세팅
		page.setPage(nowPage, total, 3, 3);
		//방명록 리스트 조회
		List list = gDao.getAllList(page);
		
		//작성글 수 조회
		String sid = (String) session.getAttribute("SID");
		
		if(isLogin(session)) {
		int cnt = gDao.getCount(sid);
		//view 에 데이터 심고
		mv.addObject("CNT",cnt);
		}
		
		mv.addObject("LIST", list);
		mv.addObject("PAGE",page);
		
		
		//뷰 부르고
		String view = "gBoard/gBoardList";
		mv.setViewName(view);
		return mv;
	}
	
	//방명록 글쓰기 폼보기 요청 처리함수
	@RequestMapping("/gBoardWrite.cls")
	public ModelAndView gBoardWrite(HttpSession session, ModelAndView mv, RedirectView rv) {
		//로그인 체크
		String sid =(String) session.getAttribute("SID");
		if(sid == null) {
			rv.setUrl("/cls2/member/login.cls");
			mv.setView(rv);
			return mv;
		}
		//작성한 글 수 조회
		int cnt = gDao.getCount(sid);
		if(cnt != 0) {
			rv.setUrl("/cls2/gBoard/gBoardList.cls");
			mv.setView(rv);
			return mv;
		}
		BoardVO bVO = gDao.writerInfo(sid);
		System.out.println("######### id : " + sid);
		System.out.println("######### avatar : " + bVO.getAvatar());
		mv.addObject("DATA", bVO);
		
		String view = "gBoard/gBoardWrite";
		mv.setViewName(view);
		return mv;
	}
	
	//방명록 글등록 요청 처리함수
	@RequestMapping("/gBoardWriteProc.cls")
	public ModelAndView addGBoard(BoardVO bVO, HttpSession session, ModelAndView mv, RedirectView rv) {
		//로그인 체크
		String sid = (String) session.getAttribute("SID");
		if(sid == null) {
			//로그인 안된 경우
			rv.setUrl("/cls2/member/login.cls");
			mv.setView(rv);
			return mv;
		}
		//데이터베이스에 글 등록하고
		int cnt = gDao.addBoard(bVO);
		//결과에 따라 처리해주고
		if(cnt != 1) {
			//등록작업 실패
			rv.setUrl("/cls2/gBoard/gBoardWrite.cls");
		}else {
			rv.setUrl("/cls2/gBoard/gBoardList.cls");
		}
		//데이터 반환하고
		mv.setView(rv);
		
		
		//mv.setViewName("gBoard/gBoardWrite");
		return mv;
	}
	
	public boolean isLogin(HttpSession session) {
		String sid = (String) session.getAttribute("SID");
		return (sid ==null)? false : true ;
	}
}
