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
				var body =$('#body').val();
				if(!(title && body)){
					alert('#필수 입력사항을 확인하세요');
					return ;
				}
				
				$('#frm').submit();
				return;
			case 'ebtn' :
				var title=$('#title').val();
				var body =$('#body').val().trim();
				if(!(title && body) || (title =='${DATA.title}' && body == '${DATA.body.trim()}')){
					alert('***수정내용을 확인하세요');
					//이 함수 즉시 종료
					return;
				}
			
				if(title == '${DATA.title}'){
					//이 경우는 제목을 수정 안한 경우이므로
					//이 태그를 전송하면 안된다.
					$('#title').prop('disabled',true);
				}else{
					$('title').prop('disabled', false);
					//글 제목의 정규표현식 검사를 한다.
					var exp=/^.{1,50}$/;
					var result = exp.test(title);
					if(!result){
						//이 경우는 정규표현식에 통과하지 못한 경우
						//따라서 표현식에 맞도록 유도한다.
						alert('#제목은 50글자 까지만 가능합니다.');
						return;
					}
				}
				
				if(body =='${DATA.body.trim()}'){
					$('#body').prop('disabled',true);
				} else {
					$('#body').prop('disabled', false);
				}
				
				//이 행을 수정하는 경우는 제목과 본문 중 적어도 한개는 수정이 되었고
				//제목의 글자 수도 50자 이내로 작성한 경우이므로 처리페이지를 부른다.
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
			<h1 class="w3-green w3-center w3-padding mg0">게시글 수정</h1>
		</header>
		
		<section class="w3-col">
			<div class="w3-col w3-round-large w3-card-4 w3-margin-bottom w3-padding">
				<div class="w3-col w120 w3-center pdAll10">
					<img src="/cls2/img/avatar/${DATA.avatar}" class="inblock w3-circle avtBox100 border3px w3-card-2">
					<span class="w3-col mgt10 ft10"><b>${SID}</b></span>
				</div>
				<div class="w3-rest w3-padding h100per">
				<form  method ="POST" action="/cls2/reBoard/reBoardEditProc.cls" name="frm" id="frm" class="w3-col">
					<input type="hidden" name="bno" id="bno" value="${DATA.bno}">
					<input type="hidden" name="nowPage" value="${nowPage}">
				<c:if test="${DATA.upno ne 0}">	
					<input type="hidden" name="upno" id="upno" value="${DATA.upno}" disabled>
					<div class="w3-col ft14">
						<div class="w3-col ">
							<span class="w3-col m2 mgt10 ft14 w3-text-grey"><label for="title" ><b>원글제목</b></label></span>
							<div class="w3-col m10 pdl10">
								<input type="text" class="w3-input w3-boreder w3-text-blue w3-border-grey ft14" value="${DATA.uptitle}" disabled>
							</div>
						</div>
					</div>
				</c:if>		
						<div class="w3-col w3-margin-top">
							<span class="w3-col m2 mgt10 ft14 w3-text-grey"><label for="title" ><b>글제목</b></label></span>
							<div class="w3-col m10 pdl10">
								<input type="text" name="title" id="title"
										class="w3-input w3-boreder w3-border-grey"
										placeholder="글제목을 입력하세요!" value="${DATA.title}">
							</div>
						</div>
						<div class="w3-col w3-margin-top">
							<div class="w3-col ">
								<span class="w3-col m2 mgt10 ft14 w3-text-grey"><label for="body"><b>글내용</b></label></span>
								<div class="w3-col m10">
									<textarea row="7" class="w3-input w3-border w3-border-grey noresize" name="body" id="body" placeholder="본문내용을 입력하세요" >${DATA.body}</textarea>							
								</div>
							</div>
						</div>
				</form>
				</div>
				</div>
			</section>
			
			<footer class="w3-col w3-card-4">
				<div class="w3-third w150 w3-left w3-button w3-small w3-green" id="hbtn">home</div>
				<div class="w3-third w150 w3-left w3-button w3-small w3-lime w3-right" id="rbtn">reset</div>
				<div class="w3-third w150 w3-left w3-button w3-small w3-blue w3-right" id="ebtn">댓글수정</div>
			</footer>

	</div>
</body>
</html>