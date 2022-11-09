<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- <div style="background-color: #00008b; color: #ffffff; height: 20px; --%>
<%-- <!-- 	padding: 5px;"> --> --%>
	
<!-- 	SPMS(Simple Project Management System) -->


	
	<div align="center">
		<ul id="ul">
			<li><a>무비요</a></li>
			<li><button>순위</button></li><li><button>평점</button></li>
			<li><button>장르</button></li><li><button>영빈님을 위한 추천</button></li>
		</ul>
	</div>
	<div style="background-color: black; color: #ffffff; height: 70px; padding-right: 50px;">	
	<c:if test="${member.email ne null}">
		<span style="float: right;">
			${member.name}
			<a style="color:white;" 
				href="<%=request.getContextPath()%>/auth/logout.do">
				로그아웃
			</a>
		</span>	
	</c:if>
	
	<c:if test="${member.email eq null}">
		<span style="float: right;">
			게스트 로그인중
			<a style="color:white;" href="<%=request.getContextPath()%>/auth/login.do">
				로그인
			</a>
		</span>
	</c:if>
		<input type="text" class="input" placeholder="영화를 검색해보세요" style="float: right;">
	</div>
	
<!-- 	</div> -->
	


<!-- </div> -->
	