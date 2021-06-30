<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/cls2/css/w3.css">
<link rel="stylesheet" type="text/css" href="/cls2/css/user.css">
<script type="text/javascript" src="/cls2/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="/cls2/js/w3color.js"></script>
<style type="text/css">


</style>
<script type="text/javascript">

	$(document).ready(function(){
		$('#hbtn').click(function(){
			location.href = '/cls2/main.cls';
		});
		$('#lbtn').click(function(){
			$('#frm').submit();
		});		
		
		$('#ebtn').click(function(){
			$('#frm').attr('action', '/cls2/board/boardEdit.cls');
			$('#frm').submit();
		});
	});
</script>
</head>
<body>
<form method="POST" action="/cls2/board/boardEdit.cls" id="frm" name="frm">
	<input type="hidden"name="nowPage" id="nowPage" value="${nowPage}"><!-- 파라미터가 아닌 속성에서 꺼냄 -->
	<input type="hidden" name="bno" id="bno" value="${DATA.bno}">
</form>
<div class="w3-content mxw750 w3-margin-top">
		<div class="w3-col w3-card-4 mgb20">
			<h1 class="w3-green w3-center w3-padding mg0 w3-margin-bottom">${DATA.id}님 글 상세보기</h1>

			<div class="w3-col w3-padding w3-margin-bottom w3-card-4">
				<div class ="w3-col w3-margin-top w3-margin-bottom">
					<label for="title" class="w3-col w150 w3-center w3-text-blue ft14">작성자</label>
					<div class="w3-rest">
						<label  class="w3-rest w3-input w3-round-large w3-border">${DATA.id}</label>
					</div>
				</div>
				<div class ="w3-col w3-margin-top w3-margin-bottom">
					<label for="title" class="w3-col w150 w3-center w3-text-blue ft14">작성일</label>
					<div class="w3-rest">
						<label  class="w3-rest w3-input w3-round-large w3-border">${DATA.sdate}</label>
					</div>
				</div>
				<div class ="w3-col w3-margin-top w3-margin-bottom">
					<label for="title" class="w3-col w150 w3-center w3-text-blue ft14">글제목</label>
					<div class="w3-rest">
						<label  class="w3-rest w3-input w3-round-large w3-border">${DATA.title}</label>
					</div>
				</div>
				<div class ="w3-col w3-margin-top w3-margin-bottom pdb10 w3-border-light-grey">
					<label  class="w3-col w150 w3-center w3-text-grey ft14">첨부파일</label>
					<div class="w3-rest pdr30">
					<div class="w3-col w3-margin-top w3-center pdAll10">
				<c:if test="${empty LIST}">
					첨부파일없음
				</c:if>
				<c:forEach var="data" items="${LIST}">	
							<div class="inblock box120 pdAll10 w3-border w3-border-grey w3-card mgl10">
								<div class="w3-col imgBox100" >
									<img class="w3-col img100" src="/cls2/img/upload/${data.savename}">
								</div>
							</div>
				</c:forEach>	 
						</div>
					</div>
					<div class ="w3-col w3-margin-bottom">
						<label class="w3-col w150 w3-center w3-text-grey ft14">글내용</label>
						<div class="w3-rest pdr30">
							<div class="w3-rest w3-container w3-input w3-border noresize">${DATA.body}
							</div>
						</div>
					</div>
				
				</div>
<!-- 버튼 태그 -->
				<div class="w3-col w3-margin-top w3-card-4">
					<div class="w3-third w3-green w3-hover-lime w3-button" id="hbtn">home</div>
					<div class="w3-third w3-blue w3-hover-pink w3-button" id="lbtn">List</div>
					<div class="w3-third w3-orange w3-hover-red w3-button w3-hidden" id="ebtn">edit</div>
				
				</div> 		
			</div>
		</div>	
	</div>	
</body>
</html>