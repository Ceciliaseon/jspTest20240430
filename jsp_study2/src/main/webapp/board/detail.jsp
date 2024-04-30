<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</head>
<body>
	<h1>Board Detail Page</h1>
	
	<table class="table table-bordered w-50 p-3">
		<tr>
			<td>num</td>
			<td>${bvo.bno }</td>
		</tr>
		<tr>
			<td>제목</td>
			<td>${bvo.title }</td>
		</tr>
		<tr>
			<td>작성자</td>
			<td>${bvo.writer }</td>
		</tr>
		<tr>
			<td>내용</td>
			<td>${bvo.content }</td>
		</tr>
		<tr>
			<td>이미지</td>
			<td><img alt="" src="/_fileUpload/${bvo.imageFile }"></td> 
		</tr>
		<tr>
			<td>작성일</td>
			<td>${bvo.regdate }</td>
		</tr>
	</table>
	<c:if test="${bvo.writer eq ses.id }">
		<a href="/brd/modify?bno=${bvo.bno}"><button type="button">수정</button></a>
		<a href="/brd/delete?bno=${bvo.bno}"><button type="button">삭제</button></a>	
	</c:if>
	<a href="/brd/list"><button type="button">list</button></a>
	<a href="../index.jsp"><button type="button">index</button></a>
	
	<!-- comment line -->
	<hr>
	
	<!-- comment 등록 -->
	<div>
		comment line <br>
		<input type="text" id="cmtWriter" value="${ses.id }" readonly="readonly">
		<input type="text" id="cmtText" placeholder="Add Comment...">
		<button type="button" id="cmtAddBtn">comment post</button>
	</div>
	
	<br> <hr>
	
	<!-- commtne 표시(출력) -->
		<div id="commentLine">
			<div>
				<div>cno, bno, writer, regdate</div>
				<div>
					<button type="button">수정</button>
					<button type="button">삭제</button> <br>
					<input type="text" value="content">
				</div>
			</div>
		</div>
	
	<script type="text/javascript">
		const bnoVal = 	`<c:out value="${bvo.bno }" />`;
		const id = `<c:out value="${ses.id}" />`;
		console.log(bnoVal);
		console.log(id);
	</script>
	
	<script type="text/javascript" src="/resources/board_detail.js"></script>
	
	<script type="text/javascript">
		printCommentList(bnoVal);
	</script>
</body>
</html>