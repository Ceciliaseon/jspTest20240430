<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Board Register Page</h1>
	
	<!-- enctype="multipart/form-data"> : 이미지 파일을 가져갈 때 사용하는 타입 -->
	<!-- 파일 : 종류/확장자  image/jpg  image/png -->
	
	
	<form action="/brd/insert" method="post" enctype="multipart/form-data">
		title : <input type="text" name="title"> <br>
		writer : <input type="text" name="writer" value="${ses.id }" readonly="readonly"> <br>
		content : <textarea rows="10" cols="40" name="content"></textarea> <br>
		첨부파일 : <input type="file" name="imageFile" accept="image/png, image/jpg, image/jpeg, image/gif"> <br>
		<button type="submit">글쓰기</button>
		<a href="../index.jsp"><button type="button">목록</button></a>
	</form>
</body>
</html>