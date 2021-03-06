
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Send message</title>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css" type="text/css" rel="stylesheet">
    <style><%@include file="/view/css/message.css"%></style>

</head>
<body>
<div class="container">
    <h3 class=" text-center">Messaging</h3>
    <div class="messaging">
        <div class="inbox_msg">
            <div class="inbox_people">
                <div class="headind_srch">
                    <div class="recent_heading">
                        <h4>Recent</h4>
                    </div>
                </div>
                <div class="inbox_chat">
                    <c:forEach items="${userList}" var="u">
                    <div class="chat_list active_chat">
                        <div class="chat_people">
                            <div class="chat_img"><a href="/facebook?action=messeage&userId=${userId}&friendId=${u.getId()}"><img src="${u.getAvatar()}" alt="${u.getAccount()}"></a> </div>
                            <div class="chat_ib">
                                <h5>${u.getAccount()}</h5>
                            </div>
                        </div>
                    </div>
                    </c:forEach>
                </div>
            </div>
            <div class="mesgs">
                <div class="msg_history">
                    <c:forEach items="${listMess}" var="mess">
                    <c:if test="${mess.getFriend_id() eq userId}">
                    <div class="incoming_msg">
                        <div class="incoming_msg_img"><img src="${mess.getAvatar()}"> </div>
                        <div class="received_msg">
                            <div class="received_withd_msg">
                                <p>${mess.getContent()}</p>
                            </div>
                        </div>
                    </div>
                    </c:if>
                    <c:if test="${mess.getUser_id() eq userId}">
                    <div class="outgoing_msg">
                        <div class="sent_msg">
                            <p>${mess.getContent()}</p>
                        </div>
                    </div>
                    </c:if>
                    </c:forEach>
                </div>
                <form method="post">
                <div class="type_msg">
                    <div class="input_msg_write">
                        <input type="text" class="write_msg" placeholder="Type a message" name="messContent"/>
                        <button class="msg_send_btn" type="submit"><i class="fa fa-paper-plane-o" aria-hidden="true"></i></button>
                    </div>
                </div>
                </form>
            </div>
        </div>
    </div></div>

</body>
</html>
