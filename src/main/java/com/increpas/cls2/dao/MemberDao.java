package com.increpas.cls2.dao;

import java.util.List;

import org.mybatis.spring.*;
import org.springframework.beans.factory.annotation.*;

import com.increpas.cls2.vo.MemberVO;

public class MemberDao {
	
	@Autowired
	SqlSessionTemplate sqlSession;
	
	public int getIdCnt(String sid) {
		return sqlSession.selectOne("mSQL.idCheck", sid); //여기에 들어가는 변수명
	}
	//로그인 데이터베이스 전담 조회 처리함수
	public int getLogin(MemberVO mVO) {
		return sqlSession.selectOne("mSQL.login", mVO);
	}
	//회원리스트조회 전담 처리함수
	public List getMembList() { // 이 리스트에는 MemberVO들이 담겨있다.
		return sqlSession.selectList("mSQL.memberList");
		
	}
	//회원번호로 정보 조회 전담 처리함수
	public MemberVO memberInfo(int mno) {
		return sqlSession.selectOne("mSQL.memberNoInfo", mno);
	}
	
	//회원정보 입력 전담 처리함수
	public int addMember(MemberVO mVO) {
		return sqlSession.insert("mSQL.addMember", mVO);
	}
	//내정보 조회 전담 처리함수
	public MemberVO getMyInfo(String id) {
		return sqlSession.selectOne("mSQL.memberIdInfo", id);
	}
	
	//내정보 수정 전담 처리 함수
	public int updateInfo(MemberVO mVO) {
		return sqlSession.update("mSQL.editMyInfo", mVO);
	}
	
	//성별 아바타 리스트조회 전담처리 함수
	public List avtGenList(String gen) {
		return sqlSession.selectList("mSQL.avtGenList", gen);
	}
}
