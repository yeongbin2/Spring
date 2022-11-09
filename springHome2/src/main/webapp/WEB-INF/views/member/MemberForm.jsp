<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>회원 등록</title>
<script type="text/javascript">
	function pageMoveListFnc() {
		location.href = './list.do';
	}
	
	window.onload = function(){
		submitFormObj = document.getElementById('submitForm');
		nameObj = document.getElementById('name');
		emailObj = document.getElementById('email');
		passwordObj = document.getElementById('password');
		submitBtnObj = document.getElementById('submitBtn');
		
		submitBtnObj.addEventListener('click', function(e){
			e.preventDefault();
			if(nameObj.value == "" || nameObj.value == null){
				alert("이름은 반드시 입력해주세요");
				nameObj.focus();
				return;
			}else if(emailObj.value == "" || emailObj.value == null){
				alert("이메일은 반드시 입력해주세요");
				emailObj.focus();
				return;
			}else if(passwordObj.value == "" || passwordObj.value == null){
				alert("비밀번호는 반드시 입력해주세요");
				passwordObj.focus();
				return;
			}else{
				alert('회원가입 성공');
				submitForm.submit();
			}
		});
	}
</script>

</head>

<body>
	
	<jsp:include page="../Header.jsp"/>
	<h1>회원등록</h1>
			
	<form action='./addCtr.do' method='post' id='submitForm'
		enctype="multipart/form-data">
		이름: <input type='text' name='name' id='name'><br>
		이메일: <input type='text' name='email' id='email'><br>
		암호: <input type='password' name='password' id='password'><br>
		파일: <input type="file" name='file'> <br>
		<input type='submit' value='추가' id='submitBtn'>
		<input type='button' value='뒤로가기' onclick='pageMoveListFnc();'>
	</form>	
	
	<jsp:include page="../Tail.jsp"/>
	
</body>

</html>