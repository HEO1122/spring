package com.increpas.cls2.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.increpas.cls2.vo.*;

/**
 * 이 클래스는 설문조사 관련 데이터베이스 작업을 전담할 클래스이다.
 * @author 허수경
 * @since 2021.06.01
 * @version  v.1.0
 * @see	
 * 		작업이력 ]
 * 			 2021.06.01	- 담당자 : 허수경
 * 							작업내용 : 클래스 제작
 * 										현재진행중인 설문 갯수 조회 함수 추가
 *
 */
public class SurveyDao {
	@Autowired
	SqlSessionTemplate sqlSession;
	
	/*
	 * 현재 진행중인 설문조사 갯수 조회전담 처리함수
	 */
	public int getPcount() {
		return sqlSession.selectOne("sSQL.psntCount");
	}
	
	/*
	 * 현재 진행중인 설문리스트 조회 담당 처리함수
	 */
	public List getList() {
		return sqlSession.selectList("sSQL.ingSrvy");
	}
	
	/*
	 * 설문문항 리스트 조회 전담 처리함수
	 */
	public List questList(int sino) {
		return sqlSession.selectList("sSQL.selQuest", sino);
	}
	/*
	 * 설문보기 리스트 조회 전담 처리함수
	 */
	public List exList(int qno) {
		return sqlSession.selectList("sSQL.selEx", qno);
	}
	/*
	 * 설문응답 추가 전담 처리함수
	 */
	public int insertAnswer(SurveyVO sVO) {
		return sqlSession.insert("sSQL.addAnswer", sVO);
	}
}
