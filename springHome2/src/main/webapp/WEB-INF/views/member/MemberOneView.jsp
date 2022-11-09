<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>		


<!DOCTYPE html>
<html>
<head>
<title>회원정보 상세페이지</title>
<script type="text/javascript" src="/springHome2/resources/js/jquery-3.6.1.js">

</script>

<script type="text/javascript">
	$(document).ready(function () {
		$('#memberName').css('background-color', '#E7E7E7');
		
		var pagingFormObj = document.getElementById('pagingForm');
		
		$('#pageMoveBtn').click(function(e){			
			pagingFormObj.submit();
		});
		
	});
	
	function pageMoveListFnc() {
		location.href = './list.do';
	}

</script>

</head>
	
<body>

	<jsp:include page="../Header.jsp" />
	
	
	
	<h1>회원정보 상세</h1>
	
	<form action='./update.do' method='get'>
		<input type='hidden' name='no' value='${memberDto.no}'><br>
		이름: <input type='text' name='name' id='memberName'
			value='${memberDto.name}' id='name' readonly="readonly"><br>
		이메일: <input type='text' name='email' 
			value='${memberDto.email}' id='email' disabled="disabled"><br>
		
		첨부파일:
		<c:choose>
			<c:when test="${empty fileList}">
					첨부파일이 없습니다.<br>			
			</c:when>
		
			<c:otherwise>
				<c:forEach var="row" items="${fileList}">
					<input type="button" value="이미지" name="file">
					${row.ORIGINAL_FILE_NAME}(${row.FILE_SIZE}kb)<br>
					<img alt="image not found" src="<c:url value='/img/${row.STORED_FILE_NAME}'/>">
					<br>
				</c:forEach>
			</c:otherwise>
		</c:choose>
		
		
		가입일: <fmt:formatDate value="${requestScope.memberDto.createDate}"
			pattern="yyyy-MM-dd hh:mm:ss" /><br>						
		<input type='submit' value='수정하기' id='submitBtn'>
		<input type='button' value='이전페이지' id='pageMoveBtn'>	
		<input type="hidden" id="curPage" name="curPage" value="${searchMap.curPage}">	
		<input type="hidden"  name="keyword" value="${searchMap.keyword}">
		<input type="hidden"  name="searchOption" value="${searchMap.searchOption}">
	</form>	
	
	<form action="./list.do" id="pagingForm" method="post">
		<input type="text" id="curPage" name="curPage" value="${searchMap.curPage}">	
		<input type="text"  name="keyword" value="${searchMap.keyword}">
		<input type="text"  name="searchOption" value="${searchMap.searchOption}">
	</form>	
	
	<jsp:include page="../Tail.jsp" />
</body>
</html>