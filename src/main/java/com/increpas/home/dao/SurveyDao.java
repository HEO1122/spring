package com.increpas.home.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

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
}
