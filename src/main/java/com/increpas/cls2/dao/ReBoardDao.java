package com.increpas.cls2.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.increpas.cls2.util.PageUtil;
import com.increpas.cls2.vo.BoardVO;

public class ReBoardDao {

	@Autowired
	SqlSessionTemplate sqlSession;
	
	//총게시물 개수 조회 전담 처리함수
	public int getTotal() {
		return sqlSession.selectOne("rSQL.getTotal");
	}
	//댓글 게시판 리스트 전담 처리함수
	public List getRnoList(PageUtil page) {
		return sqlSession.selectList("rSQL.reList", page);
	}
	//원글 등록 전담 처리함수
	public int addBoard(BoardVO bVO) {
		return sqlSession.insert("rSQL.addReply", bVO);
	}
	//댓글 등록 전담 처리함수
	public int addReply(BoardVO bVO) {
		return sqlSession.insert("rSQL.addRreply",bVO);
	}
	//게시글 삭제 전담 처리함수
	public int reboardDel(int bno) {
		return sqlSession.delete("rSQL.reboardDel", bno);
	}
	
	//게시글 수정데이터 조회 전담 처리함수
	public BoardVO getEditData(int bno) {
		return sqlSession.selectOne("rSQL.editReBoard", bno);
	}
	//게시글 수정 처리 전담 함수
	public int reBoardProc(BoardVO bVO) {
		return sqlSession.update("rSQL.editReBoardProc", bVO);
	}
}
