<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Boarde Modify Page</h1>
	
	<form action="/brd/update" method="post" enctype="multipart/form-data">
		bno : <input type="text" name="bno" value="${bvo.bno }" readonly="readonly"> <br>
		titlt : <input type="text" name="title" value="${bvo.title }"><br>
		writer : <input type="text" name="writer" value="${bvo.writer }" readonly="readonly"><br>
		content : <textarea rows="10" cols="40" name="content">${bvo.content }</textarea><br>
		image : 
			<input type="hidden" name="imageFile" value="${bvo.imageFile }">
			<input type="file" name="newFile">
			<img alt="" src="/_fileUpload/${bvo.imageFile }"> <br>
		regdate : <input type="text" name="regdate" value="${bvo.regdate }" disabled="disabled"><br>
		moddate : <input type="text" name="moddate" value="${bvo.moddate }" disabled="disabled"><br>
		<button type="submit">수정하기</button><br>
	</form>
	<a href="/brd/list"><button type="button">list</button></a>
	
</body>
</html>