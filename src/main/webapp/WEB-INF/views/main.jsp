<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>cls Main</title>
<link rel="stylesheet" type="text/css" href="/cls2/css/w3.css">
<link rel="stylesheet" type="text/css" href="/cls2/css/user.css">
<script type="text/javascript" src="/cls2/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="/cls2/js/w3color.js"></script>
<style type="text/css"></style>
<script type="text/javascript">
	$(document).ready(function(){
		// 태그라이브러리 테스트 버튼 이벤트 처리
		$('#libbtn1').click(function(){
			// 할일
			// 1. 넘겨야할 데이터 form 태그에 추가해주고
			$('#frm').append('<input type="hidden" name="code" value="taglib">');
			// 2. 보내질 주소 셋팅하고
			$('#frm').attr('action', '/cls2/j05/addMember.cls');
			// 3. 폼태그 전송하고
			$('#frm').submit();
		});
		// jQuery 처리 테스트 버튼 이벤트 처리
		$('#libbtn2').click(function(){
			// 할일
			// 1. 넘겨야할 데이터 form 태그에 추가해주고
			$('#frm').append('<input type="hidden" name="code" value="jquery">');
			// 2. 보내질 주소 셋팅하고
			$('#frm').attr('action', '/cls2/j05/addMember.cls');
			// 3. 폼태그 전송하고
			$('#frm').submit();
		});
		
		// login 버튼 클릭 이벤트 처리
		$('#login').click(function(){
			// 폼을 전송하는 방법
			$('#frm').attr('action', '/cls2/member/login.cls');
			$('#frm').submit();
		});
		
		/* 로그아웃버튼 이벤트 처리 */
		$('#logout').click(function(){
			$(location).attr('href', 'http://localhost/cls2/member/logout.cls');
		});
		
		/* 회원가입버튼 이벤트처리 */
		$('#join').click(function(){
			$(location).attr('href', 'http://localhost/cls2/member/join.cls');
		});
		
		$('#myInfo').click(function(){
			$(document.frm).attr('action', '/cls2/member/myInfo.cls');
			$(document.frm).submit();
		});
		
		$('.cls2MBtn').click(function(){
			// 어떤 버튼이 클릭되었는지 확인하고
			var tid = $(this).attr('id');
			var url = '';
			switch(tid){
			case 'cls2Login':
				url = '/cls2/member/login.cls';
				break;
			case 'cls2Join':
				url = '/cls2/member/join.cls';
				break;
			case 'cls2Logout':
				url = '/cls2/member/logout.cls';
				break;
			case 'cls2MyInfo':
				url = '/cls2/member/myInfo.cls';
				break;
			case 'membList':
				url = '/cls2/member/memberList.cls';
				break;
			case 'gboard':
				url = '/cls2/gBoard/gBoardList.cls';
				break;
			case 'reboard':
				url = '/cls2/reBoard/reBoardList.cls';
				break;
			case 'board':
				url = '/cls2/board/board.cls';
			case 'survey':
				url = '/cls2/survey/surveyList.cls';
			}
			
			$(location).attr('href', url);
		});
	});
</script>
</head>
<body>
	<!-- 데이터 전송용 form tag -->
	<form method="POST" id="frm" name="frm">
	</form>
	
	<div class="w3-content mxw700 w3-center w3-margin-top">
		<h1 class="w3-margin-top w3-blue w3-card-4 w3-padding">spring Project</h1>
			<!-- 수업 예제 링크 버튼 추가 장소 -->
			<div class="w3-col w3-padding w3-border-bottom">
				<h4 class="w3-col s4 w3-text-grey">Dispatch Controller Request</h4>
				<div class="w3-col s8">
		<c:if test="${(not empty SID) && (SCOUNT != 0) && not empty SCOUNT}">
			
					<div class="w3-col w3-yellow w3-margin-bottom w3-button cls2MBtn" id="survey">
						<h4>설문조사페이지</h4>
					</div>
			
		</c:if>	
		<c:if test="${empty SID }">
					<div class="w3-col w3-deep-purple w3-margin-bottom w3-button cls2MBtn" id="cls2Login">
						<h4>cls2 Login</h4>
					</div>
					<div class="w3-col w3-blue w3-margin-bottom w3-button cls2MBtn" id="cls2Join">
						<h4>cls2 Join</h4>
					</div>
		</c:if>
		<c:if test="${not empty SID}">
					<div class="w3-col w3-purple w3-margin-bottom w3-button cls2MBtn" id="cls2MyInfo">
						<h4>cls2 내 정보보기</h4>
					</div>
					<div class="w3-col w3-deep-purple w3-margin-bottom w3-button cls2MBtn" id="cls2Logout">
						<h4>cls2 로그아웃</h4>
					</div>
					<div class="w3-col w3-blue w3-margin-bottom w3-button cls2MBtn" id="membList">
						<h4>회원 목록 보기</h4>
					</div>
		</c:if>
				</div>
			</div>
			<!-- 댓글게시판 링크 버튼 추가 장소 -->
			<div class="w3-col w3-padding w3-border-bottom">
				<h4 class="w3-col s4 w3-text-grey">방명록</h4>
				<div class="w3-col s8">
		
					<div class="w3-col w3-blue w3-margin-bottom w3-button cls2MBtn" id="gboard">
						<h4>방명록 리스트</h4>
					</div>
			</div>
		</div>
			<div class="w3-col w3-padding w3-border-bottom">
				<h4 class="w3-col s4 w3-text-grey">댓글게시판</h4>
				<div class="w3-col s8">
					<div class="w3-col w3-cyan w3-margin-bottom w3-button cls2MBtn" id="reboard">
						<h4>댓글게시판 리스트</h4>
					</div>
			</div>
		</div>
		
	<!--  게시판 링크 버튼 추가 장소 -->
			<div class="w3-col w3-padding w3-border-bottom">
				<h4 class="w3-col s4 w3-text-grey">게시판</h4>
				<div class="w3-col s8">
					<div class="w3-col w3-green w3-margin-bottom w3-button cls2MBtn" id="board">
						<h4>게시판 리스트</h4>
					</div>
			</div>
		</div>
		
	</div>
</body>
</html>