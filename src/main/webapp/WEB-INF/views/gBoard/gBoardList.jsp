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
<style type="text/css"></style>
<script type="text/javascript">
	$(document).ready(function(){
		// 각각의 태그에 이벤트를 적용시키는 방법(<== 해당 태그를 선택해서 처리하는 방법)
		/* $("#hbtn").click(function(){
			$(location).attr('href','http://localhost/cls2/main.cls2');
		});
		$("#outbtn").click(function(){
			$(location).attr('href','/cls2/member/logout.cls2');
		});
		
		$("#lbtn").click(function(){
			$(location).attr('href','http://localhost/cls2/member/login.cls2');
		});
		
		$("#jbtn").click(function(){
			$(location).attr('href', '/cls2/member/join.cls2');
		});
		$("#wbtn").click(function(){
			$(location).attr('href','/cls2/gboard/gBoardWrite.cls2');
		});
		*/
		//여러 태그를 동시에 이벤트를 처리하는 방법
		$('.w3-button').click(function(){
			//이렇게 선택하면 클래스에 w3-button이 추가되어 있는 태그는 모두 선택하고 
			//선택될 태그들에 클릭 이벤트를 등록하게 된다.
			
			//할일 
			//어떤 태그가 클릭이 되었는지 먼저 알아낸다.
			
			var tid = $(this).attr('id');
			var url = '';
			switch(tid){
			case 'hbtn':
				url ='/cls2/main.cls';
				break;
			case 'lbtn':
				url ='/cls2/member/login.cls';
				break;
			case 'jbtn':
				url ='/cls2/member/join.cls';
				break;
			case 'outbtn':
				url ='/cls2/member/logout.cls';
				break;
			case 'wbtn':
				url ='/cls2/gBoard/gBoardWrite.cls';
				break;
			}
			$(location).attr('href', url);
		});
		//페이지 버튼 이벤트 처리
		$('.pbtn').click(function(){
			var spage = $(this).html();
			
			//alert(spage);
			
			switch(spage){
			case 'pre':
				$('#nowPage').val('${PAGE.startPage - 1}');
			break;
			case 'next':
				$('#nowPage').val('${PAGE.endPage + 1}');
			break;
			default:
				$('#nowPage').val(spage);
								
			}
				$('#frm').attr('action', '/cls2/gBoard/gBoardList.cls');
				$('#frm').submit();
		});
	});
</script>
</head>
<body>
	<div class = "w3-content mxw650 w3-margin-top">
	<header class=" w3-col w3-card-4 mgb20">
		<form method="POST" action="/cls2/reBoard/reBoardReply.cls" id="frm"name="frm">
		<input type="hidden" name="nowPage"id="nowPage"> 
		<input type="hidden" name="rno" id="rno"> 
		<input type="hidden"name="upno" id="upno"> 
		<input type="hidden" name="title" id="title">
	</form>
		<h1 class="w3-green w3-center w3-padding mg0">방명록 작성</h1>
		<nav class="w3-bar w3-red ">
			<div class="w3-col w150 w3-left w3-button w3-amber" id="hbtn">home</div>
		<c:if test="${not empty SID}">	
			<c:if test="${CNT == 0}" >
			<div class="w3-col w150 w3-right w3-button w3-lime" id="wbtn">글 작성</div>
			</c:if>
			<div class="w3-col w150 w3-right w3-button w3-light-green" id="outbtn">logout</div>
		</c:if>
		<c:if test="${empty SID}">	
			<div class="w3-col w150 w3-right w3-button w3-deep-orange" id="lbtn">login</div>
			<div class="w3-col w150 w3-right w3-button w3-yellow" id="jbtn">join</div>
		</c:if>
		</nav>
	</header>
	<c:forEach var="data" items="${LIST}">
		<div class="w3-col w3-round-large w3-card-4 w3-margin-bottom w3-padding">
			<div class = " w3-col box120 pdaAll10 w3-border-right" >
			<img src="/cls2/img/avatar/${data.avatar}" class="inblock avtBox100 w3-border w3-border-grey">
			</div>
			<div class="w3-rest w3-padding">
				<div class ="w3-col w3-border-bottom">
				<span class="w3-text-left mgb10 ft10"><b>${data.id}</b></span>	
				<span class="w3-right mgb10 ft10">${data.wdate}</span>	
				</div>
				<div class="w3-col w3-margin-top">
					<span class="w3-col w3-padding ft12">${data.ebody}</span>
				</div>
			</div>
		</div>
	</c:forEach>	
	<div class="w3-center w3-margin-bottom  ">
			<div class="w3-bar w3-border w3-round w3-margin-top">
			<c:if test="${PAGE.startPage ==1}">
					<span class="w3-bar-item w3-grey">pre</span>
			</c:if>
			<c:if test="${PAGE.startPage !=1}">
					<span class="w3-bar-item  w3-button w3-hover-aqua pbtn">pre</span>
			</c:if>
				
				<c:forEach var="page" begin="${PAGE.startPage}"
					end="${PAGE.endPage}">
					<c:if test="${PAGE.nowPage == page}">
						<span class="w3-bar-item w3-button  w3-hover-aqua w3-blue pbtn">${page}</span>
					</c:if>
					<c:if test="${PAGE.nowPage != page}">
						<span class="w3-bar-item w3-button  w3-hover-aqua pbtn">${page}</span>
					</c:if>
				</c:forEach>
				
				<c:if test="${PAGE.endPage == PAGE.totalPage}">
					<span class="w3-bar-item w3-grey">next</span>
				</c:if>
				<c:if test="${PAGE.endPage != PAGE.totalPage}">
					<span class="w3-bar-item w3-button  w3-hover-aqua pbtn">next</span>
				</c:if>
			</div>
		</div>
		
	</div>
</body>
</html>