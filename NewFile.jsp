<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Calculator</title>
<link rel="stylesheet" type="text/css" href = "index.css">
</head>
<body>
 <div class="container">
        <h1>Calculator Result</h1>
        <h2>Answer: <%= request.getParameter("ans") %></h2>
        <h4>Joke of the Day</h4>
        <p><%= request.getParameter("joke") %></p>
        <a href="index.html" class="back-button">Go Back to Calculator</a>
    </div>
</body>
</html>