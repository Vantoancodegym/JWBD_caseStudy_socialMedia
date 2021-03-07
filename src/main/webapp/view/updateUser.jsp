
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>UPDATE Form</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">

    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

    <!-- Popper JS -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>

    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <style><%@include file="/view/css/register.css"%></style>
</head>
<body>
<div class="container">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h2 class="text-center">UPDATE Form</h2>
        </div>
        <div class="panel-body">
            <form method="post">
                <div class="form-group">
                    <label for="avatar">Avatar</label>
                    <input required="true" type="text" class="form-control" id="avatar" name="avatar" value="${user.getAvatar()}"/>
                </div>
                <div class="form-group">
                    <label for="pwd">Password:</label>
                    <input placeholder="Password" required="true" type="password" class="form-control" id="pwd" name="password" value="${user.getPassword()}"/>
                </div>
                <div class="form-group">
                    <label for="confirmation_pwd">Confirmation Password:</label>
                    <input placeholder="Confirmation Password" required="true" type="password" class="form-control" id="confirmation_pwd" name="confirmation_pwd"/>
                </div>
                <div class="form-group">
                    <label for="address">Address:</label>
                    <input placeholder="Address" type="text" class="form-control" id="address" name="address" value="${user.getAddress()}"/>
                </div>
                <button class="btn btn-success" type="submit">Update</button>
                <button class="btn btn-success"><a href="/facebook?action=home&id=${userId}">Cancle</a></button>
            </form>
        </div>
    </div>
</div>
</body>
</html>