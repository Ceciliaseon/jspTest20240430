<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>회원정보수정</h1>
	
	<form action="/memb/update" method="post">
		id : <input type="text" name="id" value="${ses.id }" readonly="readonly"> <br>
		pw : <input type="text" name="pwd" value="${ses.pwd }"> <br>
		email : <input type="text" name="email" value="${ses.email }"> <br>
		age : <input type="text" name="age" value="${ses.age }"> <br>
		phone : <input type="text" name="phone" value="${ses.phone }"> <br>
		regdate : <input type="text" name="regdate" value="${ses.regdate }" disabled="disabled"> <br>
		lastlogin : <input type="text" name="lastlogin" value="${ses.lastlogin }" disabled="disabled"> <br>
		<button type="submit">수정</button>
	</form>
		<a href="/memb/delete?id=${ses.id }"><button type="button">탈퇴</button></a>
		<a href="../index.jsp"><button>index</button></a>
</body>
</html>