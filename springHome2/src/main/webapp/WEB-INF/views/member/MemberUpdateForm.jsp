<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix='fmt'%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>회원정보 수정</title>

<script type="text/javascript"
    src="/springHome2/resources/js/jquery-3.6.1.js">
</script>


<script type="text/javascript">
	
	$(function () {
		var pagingFormObj = document.getElementById('pagingForm');
		
		$('#moveUserListBtn').click(function(e){			
			pagingFormObj.submit();
		});		
		
		$("a[id ^='delete']").on('click', function (e) {
			e.preventDefault();
			deleteFileFnc($(this));
		});
	});
	
	function deleteFileFnc(obj) {
		obj.parent().remove();
	}
	
	function pageMoveListFnc() {
		location.href = './list.do';
	}
	
	function pageMoveDeleteFnc(no){
		var url = "./deleteCtr.do?no=" + no;
		location.href = url;
	}
	function pageMoveBackFnc(){
		var pageMoveBackObj = document.getElementById("pageMoveBack");
		
		pageMoveBackObj.submit();
	}

	function addFileFnc() {
		var obj = $('#fileContent');
		
		var htmlStr = "";
		
		htmlStr += '<div>';
		htmlStr += '<input type="hidden" id="fileIdx" name="fileIex" value="">';
		htmlStr += '<input type="file" id="file0" name="file0">';
		htmlStr += '<a href="#this" id="delete0">삭제</a> <br>';
		htmlStr += '</div>';

		obj.html(htmlStr);
		
		$('a[id^="delete"]').on('click', function (e) {
			e.preventDefault();
			deleteFileFnc($(this));
		});
	}	
	
	</script>
</head>
	
<body>

	
	
	
	<jsp:include page="../Header.jsp" />
	
	<h1>회원정보</h1>
	<form action='./updateCtr.do' method='post' enctype="multipart/form-data">
		<input type="hidden" name='no' 
			value='${memberDto.no}' readonly><br>
		이름: <input type='text' name='name' id='memberName'
			value='${memberDto.name}' id='name'><br>
		이메일: <input type='text' name='email' 
			value='${memberDto.email}' id='email'><br>
		비밀번호: <input type="password" name="password" value=""><br>
		
		첨부파일:
		<div id="fileContent">
			<div>
				<c:choose>
					<c:when test="${empty fileList}">
						<input type="hidden" id="fileIdx" name="fileIdx" value="">
						<input type="file" id="file0" name="file0">
						<a href="#this" id="delete0" onclick="addFileFnc();">삭제</a><br>
					</c:when>
					
					<c:otherwise>
						<c:forEach var="row" items="${fileList}" varStatus="obj">
							<input type="hidden" id="fileIdx_${obj.index}" name="fileIdx" value="${row.IDX}">
							<img alt="image not found" src='<c:url value="/img/${row.STORED_FILE_NAME}"/>'><br>
							${row.ORIGINAL_FILE_NAME} 
							<input type="file" id="file_${obj.index}" name="file_${obj.index}">
							(${row.FILE_SIZE}kb)
							<a href="#this" id="delete_${obj.index}">삭제</a><br>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		
		가입일: <fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss" 
				 value="${memberDto.createDate}"/><br>		
				 
		<input type="button" value="파일추가" onclick="addFileFnc();">
		<hr>				
		<input type='submit' value='저장' id='submitBtn'>
		<input type='button' value='삭제' 
			onclick='pageMoveDeleteFnc(${memberDto.no});'>
		<input type='button' value='뒤로가기'
			onClick='pageMoveBackFnc();'>	
		<input type='button' value='회원목록가기' id='moveUserListBtn'>	
	</form>
	
	<form action="./list.do" id="pagingForm" method="post">
		<input type="hidden" id="curPage" name="curPage" value="${curPage}">	
		<input type="hidden"  name="keyword" value="${searchMap.keyword}">
		<input type="hidden"  name="searchOption" value="${searchMap.searchOption}">
	</form>		
	
	<form action="./one.do" id="pageMoveBack" method="get">
		<input type="hidden" name="no" value="${memberDto.no}">
		<input type="hidden" id="curPage" name="curPage" value="${curPage}">	
		<input type="hidden"  name="keyword" value="${searchMap.keyword}">
		<input type="hidden"  name="searchOption" value="${searchMap.searchOption}">
	</form>
	
	<jsp:include page="../Tail.jsp" />
</body>
</html>