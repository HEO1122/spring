package com.increpas.cls2.dao;

import java.util.List;

import org.mybatis.spring.*;
import org.springframework.beans.factory.annotation.*;

import com.increpas.cls2.util.PageUtil;
import com.increpas.cls2.vo.BoardVO;

public class GBoardDao {
	@Autowired
	SqlSessionTemplate sqlSession;
	
	//총게시물 개수전담 처리함수
	public int getTotal() {
		return sqlSession.selectOne("gSQL.getTotal");
	}
	//방명록 게시글 리스트 조회 전담 처리함수
	public List getAllList(PageUtil page) {
		return sqlSession.selectList("gSQL.allList", page );
	}
	//방명록 작성글 개수 조회 전담 처리함수
	public int getCount(String id) {
		return sqlSession.selectOne("gSQL.getCount", id);
	}
	//작성자 정보조회 전담 처리함수
	public BoardVO writerInfo(String id) {
		return sqlSession.selectOne("gSQL.writerInfo", id);
	}
	//방명록 등록 전담 처리함수
	public int addBoard(BoardVO bVO) {
		return sqlSession.insert("gSQL.addGBoard", bVO);
	}
}
