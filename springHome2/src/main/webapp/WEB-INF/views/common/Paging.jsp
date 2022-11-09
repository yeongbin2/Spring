<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>		

<style type="text/css">
	nav > ul{
		list-style-type: none;
		padding: 0px;
		overflow: hidden;
		background-color: #333333;
/* 		width: 600px; */
		display: table;
		margin-left: auto;
		margin-right: auto; 
	}
	
	nav > ul > li{
		float: left;
	}
	
	nav > ul > li > a{
		display: block;
		color: white;
		text-align: center;
		padding: 16px;
		text-decoration: none;
	}
	
	/* 뭘 선택했는지 알 수 있게 하려고 a에 hover(마우스를 해당 태그에 올렸을때 효과) 효과와 색깔 변화, 글자 굵게 효과를 줌 */
	nav > ul > li > a:hover {
		color: #FFD9EC;
		background-color: #5D5D5D;
		font-weight: bold;	
	}	
	
	.active{
		color: #FFD9EC;
		background-color: #5D5D5D;
		font-weight: bold;
	}
</style>

<!-- jquery경로는 반드시 resources에 담겨야 한다 (프로젝트상 경로 - webapp/resources/js/jquery.js 
	 보안을 위해서는 cdn이 아닌 프로젝트 내부에 js를 작성한다 (cdn은 인터넷 기반이므로 내부망으로는 사용불가)-->
<!-- <script type="text/javascript" src="/springHome2/resources/js/jquery-3.6.1.js"></script> -->

<!-- other cdn - GoogleCdn을 통해서 jquery를 사용 cdn을 사용할경우 버전변경도 유연함-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<script type="text/javascript">
	function goPageFnc(pageNumber){
// 		var curPageObj = document.getElementById('curPage');
		
// 		//curPage의 값을 내가 선택한 num의값으로 변경시킨다 (디폴트 값 1 - Controller에 선언)
// 		curPageObj.value = pageNumber;
		
// 		var pagingFormObj = document.getElementById('pagingForm');
// 		pagingFormObj.submit();		
		var curPageObj = $("#curPage");
		curPageObj.val(pageNumber);
		
// 		alert(curPageObj.val());
		
		$("#pagingForm").submit();
	}
	
	function goPageNextFnc(pageNumber){
		var curPageObj = document.getElementById('curPage');
		
		curPageObj.value = pageNumber;		
		
		var pagingFormObj = document.getElementById('pagingForm');
		pagingFormObj.submit();	
	}
	
	function goPageBeforeFnc(pageNumber){
		var curPageObj = document.getElementById('curPage');
		
		var curPageValue = curPageObj.value; 
		
		curPageObj.value = pageNumber;
		
		var pagingFormObj = document.getElementById('pagingForm');
		pagingFormObj.submit();	
	}
	
	$(function(){
		
		var pageButtonIdStr = '#pageButton' + $('#curPage').val();
		$(pageButtonIdStr).addClass('active');
		
	});

</script>
	<nav>
		<ul>
		<c:if test="${pagingMap.memberPaging.prevBlock ne 1}">
			<li>
				<a href="#" onclick="goPageBeforeFnc(${pagingMap.memberPaging.prevBlock});">
					<span>&laquo;</span>
				</a>
			</li>
		</c:if>
		
		<!-- goPageFnc(${num})가 forEach문을 통해 goPageFnc(1), goPageFnc(2), goPageFnc(3)이 생성된다 -->
		<c:forEach var="num"
			begin="${pagingMap.memberPaging.blockBegin}"
		 	end="${pagingMap.memberPaging.blockEnd}">
			<li id='pageButton${num}'>
				<a href="#" onclick="goPageFnc(${num});">
					<c:out value="${num}"/>
				</a>
			</li>			
		</c:forEach>	

		<c:if test="${pagingMap.memberPaging.blockEnd
			< pagingMap.memberPaging.totPage}">
			<li>
				<a href="#" onclick="goPageNextFnc(${pagingMap.memberPaging.nextBlock});">
					<span>&raquo;</span>
				</a>
			</li>
		</c:if>
		</ul>
	</nav>