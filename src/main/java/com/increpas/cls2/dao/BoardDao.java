package com.increpas.cls2.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.increpas.cls2.util.PageUtil;
import com.increpas.cls2.vo.BoardVO;
import com.increpas.cls2.vo.FileVO;

public class BoardDao {
	@Autowired
	SqlSessionTemplate sqlSession;
	
	//게시글 총 페이지 전담 함수
	public int total() {
		return sqlSession.selectOne("bSQL.totalCnt");
	}
	//게시글 리스트 불러오는 함수
	public List boardList(PageUtil page) {
		return sqlSession.selectList("bSQL.boardList", page);
	}
	//게시글 상세 보기 함수
	public BoardVO boardDetail(int bno) {
		return sqlSession.selectOne("bSQL.boardDetail", bno);
	}
	//첨부파일 저장이름 조회 처리함수
	public List subFileList(int bno) {
		return sqlSession.selectList("bSQL.subFile",bno);
	}
	//게시글 등록 전담 처리함수
	public int addBoard(BoardVO bVO) {
		return sqlSession.insert("bSQL.addBoard", bVO);
	}
	//파일정보 등록전담 처리함수
	public int addFile(FileVO fVO) {
		return sqlSession.insert("bSQL.addFile", fVO);
	}
	//첨부파일 삭제 전담 처리함수
	public int delSub(int fno) {
		return sqlSession.update("bSQL.delSub", fno);
	}
	//게시글 삭제 전담 처리함수
	public int boardDel(int bno) {
		return sqlSession.update("bSQL.boardDel", bno);
	}
	//게시글 수정 전담 처리 함수
	public int boardEdit(BoardVO bVO) {
		return sqlSession.update("bSQL.editBoard", bVO);
	}
}
