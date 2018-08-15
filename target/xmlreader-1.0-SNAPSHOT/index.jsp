<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>File Upload Example in JSP and Servlet - Java web application</title>
</head>

<body>
<div>
    <h3> Choose File to Upload in Server </h3>
    <form action="upload" method="post" enctype="multipart/form-data">
        <p><input type="file" name="file"/></p>
        <p><input type="submit" value="upload"/></p>

    </form>
</div>

</body>
</html>