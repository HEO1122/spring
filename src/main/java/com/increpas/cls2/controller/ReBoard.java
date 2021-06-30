package com.increpas.cls2.controller;

import java.util.List;

import javax.servlet.http.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.increpas.cls2.dao.GBoardDao;
import com.increpas.cls2.dao.ReBoardDao;
import com.increpas.cls2.util.PageUtil;
import com.increpas.cls2.vo.BoardVO;

/**
 * 이클래스는 댓글 게시판 관련 요청을 처리할 컨트롤러 클래스
 * @since 2021.05.18
 * @version v.1.0
 * @see 
 * 		작업이력 ]
 * 				2021.05.18 - 허수경
 * 				작업내용 : 클래스 생성
 */
@Controller
@RequestMapping("/reBoard/")
public class ReBoard {
	
	@Autowired
	ReBoardDao rDao;
	@Autowired
	GBoardDao gDao; 
	
	//게시글 리스트 폼보기 요청 처리함수
	@RequestMapping("/reBoardList.cls")
	public ModelAndView reBoardList(PageUtil page, ModelAndView mv)
	{
		int nowPage = page.getNowPage();
		if(nowPage == 0) {
			nowPage = 1;
		}
		int total = rDao.getTotal();
		//페이지유틸 세팅
		page.setPage(nowPage, total, 3, 3);
		List list = rDao.getRnoList(page);
		
		
		mv.addObject("LIST", list);
		mv.addObject("PAGE", page);
		
		String view = "reBoard/reBoardList";
		mv.setViewName(view);
		return mv;
	}
	
	//게시글 작성 폼보기 요청 처리함수
	@RequestMapping("/reBoardWrite.cls")
	public ModelAndView reBoardWrite(ModelAndView mv, HttpSession session, RedirectView rv) {
		String sid = (String)session.getAttribute("SID");
		if(sid == null) {
			rv.setUrl("/cls2/member/login.cls");
			mv.setView(rv);
		}
		BoardVO bVO = gDao.writerInfo(sid);
		
		//데이터 전달하고
		mv.addObject("DATA", bVO);
		//뷰 부르고
		mv.setViewName("reBoard/reBoardWrite");
		return mv;
	}
	
	//게시글 등록 요청 전담 처리함수
	@RequestMapping("/reBoardWriteProc.cls")
	public ModelAndView reBoardWriteProc(BoardVO bVO, ModelAndView mv, HttpSession session, RedirectView rv) {
		
		/*
		 * 스프링에서 vo를 매개변수로 선언하면
		 * 뷰에서 전달되는 파라미터의 키값에 해당하는 
		 * VO의 변수를 차자서 자동으로 넘겨지는 파라미터를 VO에
		 * 스프링이 채워준다.
		 */
		
		//세션검사하고
		String sid = (String) session.getAttribute("SID");
		if(sid == null) {
			rv.setUrl("/.cls2/member/login.cls");
			mv.setView(rv);
			return mv;
		}
		//데이터베이스에 데이터 입력한다.
		int cnt = rDao.addBoard(bVO);
		if(cnt == 1) {
			//성공한 경우
			rv.setUrl("/cls2/reBoard/reBoardList.cls");
		}else {
			rv.setUrl("/cls2/reBoard/reBoardWrite.cls");
		}
		mv.setView(rv);
		return mv;
	}
	//댓글 달기 폼보기 요청 처리함수
	@RequestMapping("/reBoardReply.cls")
	public ModelAndView reBoardReply(BoardVO bVO, int nowPage, ModelAndView mv, HttpSession session, RedirectView rv) {
		
		//할일
		//1. 세션 검사하고
		String sid = (String) session.getAttribute("SID");
		if(sid == null) {
			rv.setUrl("/cls2/member/login.cls");
			mv.setView(rv);
			return mv;
		}
		
		int upno = bVO.getUpno();
		String title = bVO.getTitle();
		
		//2. 회원정보 가져오고
		BoardVO data = gDao.writerInfo(sid);
		// => 이 bVO에는 회원번호와 아바타 저장이름만 기억되어있다.
		data.setUpno(upno);
		data.setTitle(title);
		
		//3. 데이터 뷰에 전달하고
		mv.addObject("DATA", data);
		mv.addObject("nowPage", nowPage);
		//4. 뷰 부르고
		mv.setViewName("reBoard/reBoardReply");
		//5. 반환값 반환하고
		return mv;
	}
	
	//댓글 등록 요청
	@RequestMapping("/reBoardReplyProc.cls")
	public ModelAndView reBoardReplyProc(int nowPage,BoardVO bVO, ModelAndView mv, HttpSession session, RedirectView rv) {
		//할일
		//1. 로그인 체크
		String sid = (String) session.getAttribute("SID");
		if(sid == null) {
			rv.setUrl("/cls2/member/login.cls");
			mv.setView(rv);
			return mv;
		}
		
		System.out.println("******** proc nowPage : " + nowPage);
		//데이터 베이스 작업
		int cnt = rDao.addReply(bVO);
		
		//결과에 따라 처리하고
		if(cnt ==1) {
			//등록에 성공한 경우
			rv.setUrl("/cls2/reBoard/reBoardList.cls?nowPage=" + nowPage);
		}else {
			rv.setUrl("/cls2/reBoard/reBoardReply.cls");
		}
		mv.setView(rv);
		
		return mv;
	}
	@RequestMapping("/reBoardDel.cls")
	//게시글 삭제 요청 처리함수
	public ModelAndView reBoardDel(int bno, int nowPage, ModelAndView mv, HttpSession session, RedirectView rv) {
		//할일
		//1. 로그인 검사하고
		String sid = (String) session.getAttribute("SID");
		if(sid == null) {
			rv.setUrl("/cls2/member/login.cls");
			mv.setView(rv);
			return mv;
		}
		String view ="reBoard/redirectView";
		//2. 데이터베이스 작업하고
		int cnt = rDao.reboardDel(bno);
		//3. 결과에 따라서 처리하고(데이터 + 뷰)
		if(cnt == 0) {
			//작업에 실패한 경우
			mv.addObject("MSG", "삭제실패");
					
		}else {
			//삭제처리 작업에 성공한 경우
			mv.addObject("MSG", cnt+"개의 게시물 삭제성공");
			
		}
		
		mv.addObject("nowPage", nowPage);
		mv.addObject("PATH", "/cls2/reBoard/reBoardList.cls");
		mv.setViewName(view);
		//4. 반환값 반환하고
		
		return mv;
	}
	//게시글 수정 폼보기 요청
	@RequestMapping("/reBoardEdit.cls")
	public ModelAndView reBoardEdit(int bno, int nowPage, ModelAndView mv, HttpSession session, RedirectView rv) {
		
		//할일
		//1.로그인 검사
		String sid  = (String) session.getAttribute("SID");
		if(sid == null) {
			rv.setUrl("/cls2/member/login.cls");
			mv.setView(rv);
			return mv;
		}
		//2. 데이터 베이스 조회
		BoardVO bVO = rDao.getEditData(bno);
		
		//3. 데이터 전달
		mv.addObject("DATA", bVO);
		mv.addObject("nowPage", nowPage);
		//4. 뷰 부르고
		mv.setViewName("reBoard/reBoardEdit");
		//5. 반환값 반환하고
		String view = "reBoard/reBoardEdit";
		mv.setViewName(view);
		return mv;
	}
	//게시글 수정 처리 요청
	@RequestMapping("/reBoardEditProc.cls")
	public ModelAndView reBoardEditProc(BoardVO bVO, int nowPage, ModelAndView mv, HttpSession session, RedirectView rv) {
		//할일
		//로그인 검사
		System.out.println("TITLE : "+bVO.getTitle()+" /BODY : " +bVO.getBody() +" /bno : "+bVO.getBno());
		String sid = (String) session.getAttribute("SID");
		if(sid == null) {
			rv.setUrl("/cls2/member/login.cls");
			mv.setView(rv);
			return mv;
		}
		//2. 데이터 베이스 작업
		int cnt = rDao.reBoardProc(bVO);
		//3. 데이터베이스 결과에 따라서 처리
		if(cnt == 1) {
			//성공
			rv.setUrl("/cls2/reBoard/reBoardList.cls?nowPage"+ nowPage);
		}else {
			//실패
			rv.setUrl("/cls2/reBoard/reBoardEdit.cls");
		}
			mv.addObject("nowPage", nowPage);
			mv.setView(rv);
		return mv;
	}
	
	
}
