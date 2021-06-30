package com.increpas.cls2.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.increpas.cls2.dao.BoardDao;
import com.increpas.cls2.service.BoardService;
import com.increpas.cls2.util.FileUtil;
import com.increpas.cls2.util.PageUtil;
import com.increpas.cls2.vo.BoardVO;
import com.increpas.cls2.vo.FileVO;
import com.increpas.home.HomeController;

/**
 * 이클래스는 게시판 관련 요청을 처리할 컨트롤러 클래스
 * @since 2021.05.18
 * @version v.1.0
 * @see 
 * 		작업이력 ]
 * 				2021.05.18 - 허수경
 * 				작업내용 : 클래스 생성
 */
@Controller
@RequestMapping("/board")
public class Board {
	
	@Autowired
	BoardDao bDao;
	@Autowired
	FileUtil fUtil;
	@Autowired
	BoardService bSrvc;
	
	private static final Logger log1 = LoggerFactory.getLogger(Board.class);
	//보드리스트 보기
	@RequestMapping("/board.cls")
	public ModelAndView gBoardList(PageUtil page, ModelAndView mv) {
		int total = bDao.total();
		//데이터 만들고
		page.setPage(page.getNowPage(), total, 5, 5);
		List list = bDao.boardList(page);
		for(Object o: list) {
			BoardVO bVO = (BoardVO) o;
			bVO.setSdate(bVO.getWdate());
			//System.out.println(bVO.getBno());
		}
		mv.addObject("PAGE", page); // 데이터 심기
		mv.addObject("LIST", list);
		String view ="board/boardList";
		mv.setViewName(view);
		return mv;
	}
	//상세보기 요청
	@RequestMapping("/boardDetail.cls")
	public ModelAndView boardDetail(int bno, int nowPage, ModelAndView mv) {
		//게시글 정보 꺼내오기
		BoardVO data = bDao.boardDetail(bno);
		data.setSdate();
		//첨부파일 리스트 꺼내오고
		List list = bDao.subFileList(bno);
		//데이터 전달하고
		mv.addObject("DATA", data);
		mv.addObject("LIST", list);
		mv.addObject("nowPage", nowPage);
		//뷰부르고
		mv.setViewName("board/boardDetail");
		return mv;
	}
	/*
	 * 작성폼보기
	 */
	@RequestMapping("/boardWrite.cls")
	public ModelAndView boardWrite(ModelAndView mv, HttpSession session, RedirectView rv) {
		//할일
		//1. 로그인 검사
		String sid = (String) session.getAttribute("SID");
		if(sid == null) {
			rv.setUrl("/cls2/member/login.cls");
			mv.setView(rv);
			return mv;
		}
		//2.뷰 셋팅
		mv.setViewName("board/boardWrite");
		//3. 반환값 반환
		return mv;
	}
	//게시글 등록 요청 처리함수
	@RequestMapping("/boardWriteProc.cls")
	public ModelAndView boardWriteProc(BoardVO bVO, ModelAndView mv, HttpSession session, RedirectView rv) {
		//할일
		//1. 로그인 검사하고
		String sid = (String) session.getAttribute("SID");
		if(sid == null) {
			rv.setUrl("/cls2/member/login.cls");
			mv.setView(rv);
			return mv;
		}
		//2. 작성자 회원번호 꺼내오고 == > 서브질의로 처리하기로 한다.
		
		//3. 게시글 데이터베이스 작업
	/*	int cnt =bDao.addBoard(bVO);
		//4. 첨부파일 작업하고
		ArrayList<FileVO> list =null;
		if(cnt ==1) {
			try {
				int count = 0;
				list = fUtil.saveProc(bVO.getFile(), bVO.getBno(),"/img/upload/");
				//list의 vo에 게시글 번호 채워주고
				for(FileVO vo : list) {
					//5. 첨부파일 데이터베이스 작업하고
					count = bDao.addFile(vo);
				}
				//6. 결과에 따라서 처리해주고
				if(count == list.size()) {
					//이 경우는 정상적으로 파일 정보를 모두 저장한 경우
					
				}else {
					//데이터베이스 작업도중 문제가 생긴경우
					// 일반적으로 트랜잭션 처리를 해줘야 한다.
					
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			rv.setUrl("/cls2/board/board.cls");
		}else {
			rv.setUrl("/cls2/board/boardWrite.cls");
		}
		*/
		try {
			
			bSrvc.insertBoard(bVO, rv);
			log1.info("*****"+ sid + "]님 게시글 등록 성공");
		}catch(Exception e) {
			log1.info("####"+ sid + "]님 게시글 등록 실패####");
			//System.out.println("게시글 추가 실패###");
		}
		
		//뷰 부르고
		mv.setView(rv);
		
		
		
		//7. 반환값 반환해주고
		return mv;
	}
	//게시글 수정 폼보기 요청
	@RequestMapping("/boardEdit.cls")
	public ModelAndView boardEdit(BoardVO bVO, ModelAndView mv, HttpSession session, RedirectView rv) {
		//할일
		//1. 세션검사
		String sid = (String) session.getAttribute("SID");
		if(sid == null) {
			rv.setUrl("/cls2/member/login.cls");
			mv.setView(rv);
			return mv;
		}
		
		//2. 데이터 가져오고
		//게시글 데이터...
		bVO = bDao.boardDetail(bVO.getBno());
		//첨부파일 데이터
		List list = bDao.subFileList(bVO.getBno());
		//3. 데이터 전달하고 
		mv.addObject("DATA", bVO);
		mv.addObject("LIST", list);
		//4. 뷰 설정하고
		mv.setViewName("board/boardEdit");
	
		//5. 반환값 반환하고
		return mv;
		
	}

	//첨부파일 삭제 요청 처리함수
	@RequestMapping("/boardImgDel.cls")
	@ResponseBody
	public Map boardImgDel(int fno, HttpSession session, RedirectView rv) {
		HashMap<String, String> map = new HashMap<String, String>();
		// 할일
		//로그인 검사
		String sid = (String) session.getAttribute("SID");
		if(sid == null) {
			map.put("result" , "REDIRECT");
		}
		//데이터베이스 작업
		int cnt = bDao.delSub(fno);
		
		//결과에 따라서 처리해주고
		if(cnt ==1) {
			map.put("result", "OK");
		}else {
			
			map.put("result", "OK");
		}
		//데이터 반환
		return map;
	}
	//게시글 삭제 요청 처리함수
	@RequestMapping("/boardDel.cls")
	public ModelAndView boardDel(int nowPage, int bno, ModelAndView mv, HttpSession session,RedirectView rv) {
		//세션 검사하고
		String sid = (String) session.getAttribute("SID");
		if(sid == null) {
			rv.setUrl("/cls2/member/login.cls");
			mv.setView(rv);
			return mv;
		}
		//데이터 베이스 처리하고
		int cnt = bDao.boardDel(bno);
		//결과에 따라서 뷰 설정하고
		if(cnt == 1) {
			//삭제에 성공한 경우
			mv.addObject("PATH", "/cls2/board/board.cls");
		}else {
			//삭제에 실패한 경우
			mv.addObject("PATH", "/cls2/board/boardEdit/cls");
			mv.addObject("BNO", bno);
		}

		
		mv.addObject("nowPage", nowPage);
		mv.setViewName("board/redirectView");
		//반환해주고
		
		return mv;
	}
	//게시글 수정 요청처리함수
	@RequestMapping("/boardEditProc.cls")
	public ModelAndView boardEditProc(BoardVO bVO, int nowPage, ModelAndView mv, HttpSession session, RedirectView rv ) {
		
		//세션검사하고
		String sid = (String) session.getAttribute("SID");
		if(sid == null) {
			rv.setUrl("/cls2/member/login.cls");
			mv.setView(rv);
			return mv;
		}
		
		mv.addObject("nowPage", nowPage);
		mv.addObject("BNO", bVO.getBno());
		/*
		//게시글 수정 데이터베이스 처리
		if(bVO.getTitle() != null || bVO.getBody() != null) {
			int cnt = bDao.boardEdit(bVO);
			if(cnt != 1) {
				//수정 작업에 실패한 경우
				mv.addObject("PATH", "/cls2/board/boardEdit.cls");
				return mv;
			}
		}
		if(bVO.getFile().length != 0 ) {
			//이 경우는 첨부한 파일이 존재하는 경우
			ArrayList<FileVO> list = null;
			try{
				list = fUtil.saveProc(bVO.getFile(), bVO.getBno(), "/img/upload/");
				int count = 0;
				for(FileVO vo : list) {
					count += bDao.addFile(vo);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		mv.addObject("PATH","/cls2/board/boardDetail.cls");
		mv.setViewName("board/redirectView");
		*/
		try {
				
				bSrvc.editBoard(bVO, rv,mv);
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("게시글 수정 실패###");
		}
		return mv;
	}
}
