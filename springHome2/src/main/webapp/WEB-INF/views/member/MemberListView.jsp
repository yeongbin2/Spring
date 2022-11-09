<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>		

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>회원목록</title>

<style type="text/css">
	table, tr, td, th{
		border:1px solid black; 
	}
	
	table {
		border-collapse: collapse;
	}
	#tdId{
	width: 500px;
	height: 500px;
	text-align: center;
	font-weight: bolder;
	}
	
	#myform fieldset{
    display: inline-block;
    direction: rtl;
    border:0;
}
#myform fieldset legend{
    text-align: right;
}
#myform input[type=radio]{
    display: none;
}
#myform label{
    font-size: 3em;
    color: transparent;
    text-shadow: 0 0 0 #f0f0f0;
}
#myform label:hover{
    text-shadow: 0 0 0 rgba(250, 208, 0, 0.99);
}
#myform label:hover ~ label{
    text-shadow: 0 0 0 rgba(250, 208, 0, 0.99);
}
#myform input[type=radio]:checked ~ label{
    text-shadow: 0 0 0 rgba(250, 208, 0, 0.99);
}
#reviewContents {
    width: 100%;
    height: 150px;
    padding: 10px;
    box-sizing: border-box;
    border: solid 1.5px #D3D3D3;
    border-radius: 5px;
    font-size: 16px;
    resize: none;
}

header {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  }
  
#ul li {
  list-style-type: none;
  float: left;
  margin-left: 10px;
  }
  

</style>
<script type="text/javascript" src="/springHome2/resources/js/jquery-3.6.1.js"></script>

<script type="text/javascript">
	function pageMoveMemberDetailFnc() {
		var memberDatailCurPageObj = document.getElementById("memberDatailCurPage");
		
		memberDatailCurPageObj.value = document.getElementById("curPage").value;
		
		var memberDetailFormObj = document.getElementById("memberDetailForm");
		
		memberDetailFormObj.submit();
	}
</script>
</head>

<body>
	<header>
	<jsp:include page="/WEB-INF/views/Header.jsp"/>
	</header>
	<h1 style="height: 50px; padding-top: 50px;">회원목록</h1>
	
	<p>
		<a href="./add.do">신규 회원 추가</a>
	</p>
	
	<form action="./list.do" method="post">
		<select name="searchOption">
			<c:choose>
				<c:when test="${searchMap.searchOption == 'all'}">
					<option value="all"<c:if test="${searchMap.searchOption eq 'all'}">selected</c:if>>이름+이메일</option>
					<option value="name"> 이름</option>
					<option value="email">이메일</option>					
				</c:when>
				<c:when test="${searchMap.searchOption == 'mname'}">
					<option value="all">이름+이메일</option>
					<option value="name"<c:if test="${searchMap.searchOption eq 'mname'}">selected</c:if>>이름</option>
					<option value="email">이메일</option>					
				</c:when>				
				<c:when test="${searchMap.searchOption == 'email'}">
					<option value="all">이름+이메일</option>
					<option value="name"> 이름</option>
					<option value="email"<c:if test="${searchMap.searchOption eq 'email'}">selected</c:if>>이메일</option>
				</c:when>				
			</c:choose>
		</select>
		
		<input type="text" name="keyword" value="${searchMap.keyword}" placeholder="회원이름 검색">
		<input type="submit" value="검색">
	</form>
	
	<br><br><br>

	<table>
		<tr>
			<th>번호</th><th>이름</th><th>이메일</th><th>가입일</th><th>꺼억</th>
		</tr>
		<c:if test="${not empty memberList}">
		<c:forEach var="memberDto" items="${memberList}">
		<tr>
			<td>${memberDto.no}</td>
			<td>
				<form id="memberDetailForm" action="./one.do" method="get">			
				<a href="#" onclick="pageMoveMemberDetailFnc();">
					${memberDto.name}
				</a>
				<input type="hidden" name="no" value="${memberDto.no}">
				<input type="hidden" id="memberDatailCurPage" name="curPage" value="">
				<input type="hidden" name="keyword" value="${searchMap.keyword}">
				<input type="hidden" name="searchOption" value="${searchMap.searchOption}">
<%-- 				<a href='./one.do?no=${memberDto.no}&searchOption=${searchMap.searchOption} --%>
<%-- 				&keyword=${searchMap.keyword}&curPage=${pagingMap.memberPaging.curPage}'>${memberDto.name}</a> --%>
				</form>
			</td>
			<td>${memberDto.email}</td>
			<td>
				<fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss" 
				 value="${memberDto.createDate}"/>	
			</td>
			<td>
				<a href='./deleteCtr.do?no=${memberDto.no}'>[삭제]</a>	
			</td>
		</tr>
		</c:forEach>
		</c:if>
		<c:if test="${empty memberList}">
			<tr>
				<td colspan="6" id="tdId">회원이 존재하지 않습니다</td>
			</tr>		
		</c:if>
	</table>
	
	<!-- jsp:include는 forward처럼 데이터를 유지시킨다 -->
	<jsp:include page="/WEB-INF/views/common/Paging.jsp"/>
	
	<form action="./list.do" id="pagingForm" method="post">
		<input type="hidden" id="curPage" name="curPage"
			value="${pagingMap.memberPaging.curPage}">	
		<input type="hidden"  name="keyword" value="${searchMap.keyword}">
		<input type="hidden"  name="searchOption" value="${searchMap.searchOption}">
	</form>
	
	
	<div hidden="">
		커렌트 블럭: <input type="text" value="${pagingMap.memberPaging.curBlock}">
		블럭 비긴 : <input type="text" value="${pagingMap.memberPaging.blockBegin}">
		블럭 엔드 : <input type="text" value="${pagingMap.memberPaging.blockEnd}">
		커렌트 페이지: <input type="text" value="${pagingMap.memberPaging.curPage}">
		커렌트 블록: <input type="text" value="${pagingMap.memberPaging.curBlock}">
		<br>
		프리브 블록: <input type="text" value="${pagingMap.memberPaging.prevBlock}">
		넥스트 블록: <input type="text" value="${pagingMap.memberPaging.nextBlock}">
		토탈 페이지: <input type="text" value="${pagingMap.memberPaging.totPage}">
		토탈 카운트: <input type="text" value="${pagingMap.totalCount}">
	</div>
	
	 <form class="mb-3" name="myform" id="myform" method="post">
		<fieldset>
			<span class="text-bold">별점을 선택해주세요</span>
			<input type="radio" name="reviewStar" value="5" id="rate1"><label
				for="rate1">★</label>
			<input type="radio" name="reviewStar" value="4" id="rate2"><label
				for="rate2">★</label>
			<input type="radio" name="reviewStar" value="3" id="rate3"><label
				for="rate3">★</label>
			<input type="radio" name="reviewStar" value="2" id="rate4"><label
				for="rate4">★</label>
			<input type="radio" name="reviewStar" value="1" id="rate5"><label
				for="rate5">★</label>
		</fieldset>
	
		<div>
			<textarea class="col-auto form-control" type="text" id="reviewContents"
					  placeholder="에베베베베"></textarea>
		</div>
	</form>
	

		
	<jsp:include page="/WEB-INF/views/Tail.jsp"/>
	
</body>

</html>