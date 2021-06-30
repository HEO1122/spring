<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
		$('.w3-button').click(function(){
			
		//어떤 부분이 클릭되었는지 확인하고
		var bid= $(this).attr("id");
		var url = '/cls2/main.cls';
		switch(bid){
		case'rbtn':
			document.frm.reset();
			return;
			
		case'wbtn':
			var title = $('#title').val().trim();
			var body =$('#body').val().trim();
			if(!(title && body)){
				alert('#필수 입력사항을 확인하세요');
				return ;
			}
			
			$('#frm').submit();
			return;
		
		
		}
		$(location).attr('href', url);
		});
		
	});
</script>
</head>
<body>
	<!--vo에 있는 변수명과 name 속성똑같이 하기 -->
	
	<div class="w3-content mxw750 w3-margin-top">
		<header class="w3-col w3-card-4 mgb20">
			<h1 class="w3-green w3-center w3-padding mg0">댓글 게시판</h1>
		</header>
		
		<section class="w3-col">
			<div class="w3-col w3-round-large w3-card-4 w3-margin-bottom w3-padding">
				<div class="w3-col w120 w3-center pdAll10">
					<img src="/cls2/img/avatar/${DATA.avatar}" class="inblock w3-circle avtBox100 border3px w3-card-2">
					<span class="w3-col mgt10 ft10"><b>${SID}</b></span>
				</div>
				<div class="w3-rest w3-padding h100per">
				<form  method ="POST" action="/cls2/reBoard/reBoardWriteProc.cls" name="frm" id="frm" class="w3-col">
					<input type="hidden" name="mno" id="mno" value="${DATA.mno}">
					<div class="w3-col ft14">
						<div class="w3-col ">
							<span class="w3-col m2 mgt10 ft14 w3-text-grey"><label for="title" ><b>글제목</b></label></span>
							<div class="w3-col m10 pdl10">
								<input type="text" name="title" id="title"
										class="w3-input w3-boreder w3-border-grey"
										placeholder="글제목을 입력하세요!">
							</div>
						</div>
						<div class="w3-col w3-margin-top">
						<div class="w3-col ">
							<span class="w3-col m2 mgt10 ft14 w3-text-grey"><label for="body"><b>글내용</b></label></span>
							<div class="w3-col m10">
								<textarea row="15" class="w3-input w3-border w3-border-grey noresize" name ="body" id="body" placeholder="본문내용을 입력하세요">
								</textarea>							
								</div>
				</form>
						</div>
						</div>
			</section>
			
			<footer class="w3-col w3-card-4">
				<div class="w3-third w150 w3-left w3-button w3-small w3-green" id="hbtn">home</div>
				<div class="w3-third w150 w3-left w3-button w3-small w3-lime w3-right" id="rbtn">reset</div>
				<div class="w3-third w150 w3-left w3-button w3-small w3-blue w3-right" id="wbtn">글작성</div>
			</footer>

	</div>
</body>
</html>