<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>Insert title here</title>

<style type="text/css">

/* display나 position을 변경시 기존 태그(ul)의 기능을 상실한다 바꾸지 말자 - 연습용으로 바꿈*/
nav > ul{
	list-style-type: none;
	padding: 0px;
	overflow: hidden;
	background-color: #333333;
	width: 600px;
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
</style>

<script type="text/javascript">
	
</script>

</head>

<body>
	<nav>
		<ul>
			<li><a href="#"><span>&laquo;</span></a></li>

			<li><a href="#">1</a></li>
			<li><a href="#">2</a></li>
			<li><a href="#">3</a></li>

			<li><a href="#"><span>&raquo;</span></a></li>
		</ul>
	</nav>
	
</body>

</html>