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
	label {
		font-size: 14pt;
	}
	
	.avtimg {
		display: inline-block;
		width: 100px;
		height: auto;
	}
</style>
<script type="text/javascript">
	$(document).ready(function(){
		$('#hbtn').click(function(){
			// addMember.jsp 페이지로 돌려보낸다.
			$(location).attr('href', '/cls2/main.cls');
		});
	
		$('#ebtn').click(function(){
			
			$(location).attr('href', '/cls2/member/myInfoEdit.cls');
		});
		
		$('#dbtn').click(function(){
			var count = 0;
			while(true){
				count++;
				var num = parseInt(Math.random()*6)+1;
				console.log(count+'번째 나온 숫자='+ num);
				if(num == 3){
					console.log('탈출');
					break;
				}
			}
			
		});
		var result = 0 ;
		var i =1;
		while(true){
			var value = window.prompt(i+"번째 입력");
			if(value =='end'){
				alert('입력이 종료되었습니다/');
				break;
			}
			result += parseInt(value);
			i++
			document.write('현재 합 :'+ result,"<br>")
		}
		 
	 
	});
</script>
</head>
<body>
<%--
	회원아이디가 
		euns 이면 농장주
		han, chae 이면 종업원
		나머지는 방문객으로 표현되도록 한다.
		
	회원의 성별은
		남자, 여자로 표현한다.
 --%>
 <button id="dbtn">주사위 던지기</button>
	<div class="w3-content mxw650 w3-center">
		<h1 class="w3-green w3-padding w3-card-4 w3-round-large">내 정보</h1>
		
		<div id="frm">
			<div class="w3-col w3-margin-top w3-card-4 w3-padding w3-round-large">
				<div class="w3-col pdt10 w3-margin-top">
					<label class="w3-col m3 w3-right-align w3-text-grey">회원번호 : </label>
					<span id="mno" class="w3-col m8 mgl20 pdl20">${DATA.mno}</span>
				</div>
				<div class="w3-col pdt10 w3-margin-top">
					<label class="w3-col m3 w3-right-align w3-text-grey">회원이름 : </label>
					<span id="name" class="w3-col m8 mgl20 pdl20">${DATA.name}</span>
				</div>
				<div class="w3-col w3-margin-top">
					<label class="w3-col m3 w3-right-align w3-text-grey">아이디 : </label>
					<span id="id" class="w3-col m8 mgl20 pdl20">${DATA.id}</span>
				</div>
				<div class="w3-col pdt10 w3-margin-top">
					<label class="w3-col m3 w3-right-align w3-text-grey">메일주소 : </label>
					<span id="mail" class="w3-col m8 mgl20 pdl20">${DATA.mail}</span>
				</div>
				<div class="w3-col pdt10 w3-margin-top">
					<label class="w3-col m3 w3-right-align w3-text-grey">전화번호 : </label>
					<span id="tel" class="w3-col m8 mgl20 pdl20">${DATA.tel}</span>
				</div>
				<div class="w3-col w3-margin-top">
					<label class="w3-col m3 w3-right-align w3-text-grey">회원성별 : </label>
					<span id="gen" class="w3-col m8 mgl20 pdl20">${DATA.gen }
					</span>
				</div>
				<div class="w3-col pdt10 w3-margin-top">
					<label class="w3-col m3 w3-right-align w3-text-grey">가입일 : </label>
					<span id="sdate" class="w3-col m8 mgl20 pdl20">${DATA.sdate}</span>
				</div>
					
				<div class="w3-col w3-margin-top w3-margin-bottom">
					<label class="w3-col m3 w3-right-align w3-text-grey">아바타 : </label>
					<div class="w3-col m8 mgl20 pdl20">
						<img src="/cls2/img/avatar/${DATA.savename}" 
								class="w3-border w3-border-grey w3-card-2 avtimg" id="avtimg${DATA.ano }">
					</div>
				</div>
			</div>
		</div>
		<div class="w3-col w3-margin-top w3-card-4 showFr">
			<div class="w3-half w3-padding w3-green w3-hover-lime" id="hbtn">home</div>
			<div class="w3-half w3-padding w3-blue w3-hover-yellow" id="ebtn">정보수정</div>
		</div>
	</div>
</body>
</html>