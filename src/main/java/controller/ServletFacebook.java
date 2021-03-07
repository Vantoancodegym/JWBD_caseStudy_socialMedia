package controller;

import model.*;
import service.messeageService.MessageService;
import service.postService.PostService;
import service.commentService.CommentService;
import service.likesService.LikesService;
import service.noticeService.NoticeService;
import service.relationshipService.RelationshipService;
import service.userService.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet(name = "ServletFacebook", urlPatterns = "/facebook")
public class ServletFacebook extends HttpServlet {
    public static final int RELATIVE_ID_ACCEPT = 2;
    public static final int RELATIVE_ID_DENY = 3;
    public static final int RELATIVE_STATUS_ID_SEND_REQUEST = 1;
    private PostService postService=new PostService();
    private LikesService likesService=new LikesService();
    private CommentService commentService=new CommentService();
    private NoticeService noticeService=new NoticeService();
    private UserService userService=new UserService();
    private MessageService messageService =new MessageService();
    private RelationshipService relationshipService=new RelationshipService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action=req.getParameter("action");
        if(action==null)action="";
        switch (action){
            case "home":
                showAllPost(req,resp);
                break;
            case "likes":
                likePost(req,resp);
                break;
            case "messeage":
                showFormMesseage(req,resp);
                break;

            case "edit":
                showEditForm(req, resp);
                break;
            case "delete":
                deletePost(req, resp);
                break;
            case "profile":
                showProfile(req,resp);
                break;
            case "add":
                addFriend(req, resp);
                break;
            case "accept":
                acceptRequest(req, resp);
                break;
            case "deny":
                denyRequest(req, resp);
                break;

        }
    }
    private void addFriend(HttpServletRequest request, HttpServletResponse response) {
        int userId = Integer.parseInt(request.getParameter("userId"));
        int friendId = Integer.parseInt(request.getParameter("friendId"));
        User user=userService.findById(userId);
        int id = (int) (Math.random() * 1000);
        RelationShip relationShip = new RelationShip(id, userId, friendId, RELATIVE_STATUS_ID_SEND_REQUEST);
        int rowEffect = relationshipService.createRelative(relationShip);
        if (rowEffect > 0) {
            String content = "<img src=\""+user.getAvatar()+"\" width=\"50px\">" + user.getAccount() + " send to you add friend request "
                    + "<a href=\"/facebook?action=accept&userId=" + userId
                    + "&friendId=" + friendId+"&relationshipId=" + id  +"\"> Accept</a>  "+
                    "  <a href=\"/facebook?action=deny&userId=" + userId
                    + "&friendId=" + friendId+"&relationshipId=" + id  +"\"> Cancle</a>";
            Notice notice = new Notice(friendId, content);
            noticeService.creatNotice(notice);
        }
        try {
            response.sendRedirect("/facebook?action=home&id=" + userId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void acceptRequest(HttpServletRequest request, HttpServletResponse response) {
        int userId = Integer.parseInt(request.getParameter("userId"));
        int friendId = Integer.parseInt(request.getParameter("friendId"));
        User user=userService.findById(friendId);
        int relationshipId = Integer.parseInt(request.getParameter("relationshipId"));
        int rowEffect = relationshipService.editRelationship(RELATIVE_ID_ACCEPT,relationshipId);
        if (rowEffect > 0) {
            String content = "<img src=\""+user.getAvatar()+"\" width=\"50px\">" + user.getAccount() + " accepted request";
            Notice notice = new Notice(userId, content);
            noticeService.creatNotice(notice);
        }
        try {
            response.sendRedirect("/facebook?action=home&id=" + friendId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void denyRequest(HttpServletRequest request, HttpServletResponse response) {
        int userId = Integer.parseInt(request.getParameter("userId"));
        int friendId = Integer.parseInt(request.getParameter("friendId"));
        User user=userService.findById(friendId);
        int relationshipId = Integer.parseInt(request.getParameter("relationshipId"));
        int rowEffect = relationshipService.editRelationship(RELATIVE_ID_DENY,relationshipId);
        if (rowEffect > 0) {
            String content = "<img src=\""+user.getAvatar()+"\" width=\"50px\">" + user.getAccount() + " deny request";
            Notice notice = new Notice( userId, content);
            noticeService.creatNotice(notice);
        }
        try {
            response.sendRedirect("/facebook?action=home&id=" + friendId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void showProfile(HttpServletRequest req, HttpServletResponse resp) {
        int userId=Integer.parseInt(req.getParameter("userId"));
        int proId=Integer.parseInt(req.getParameter("proId"));
        User user=userService.findById(proId);
        List<Post> list=postService.findAllByUser(proId);
        req.setAttribute("userId",userId);
        req.setAttribute("list",list);
        req.setAttribute("user",user);
        RequestDispatcher dispatcher=req.getRequestDispatcher("view/listProfile.jsp");
        try {
            dispatcher.forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void deletePost(HttpServletRequest req, HttpServletResponse resp) {
        int postId = Integer.parseInt(req.getParameter("postId"));
        int userId=Integer.parseInt(req.getParameter("userId"));
        Post post=postService.findById(postId);
        if (userId==post.getUser_id()) {
            likesService.deleteByPostId(postId);
            commentService.deleteByPostId(postId);
            postService.delete(postId);
            try {
                resp.sendRedirect("/facebook?action=home&id=" + userId);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                resp.sendRedirect("/facebook?action=home&id=" + userId);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) {
        int postId = Integer.parseInt(req.getParameter("postId"));
        int userId=Integer.parseInt(req.getParameter("userId"));
        Post post = postService.findById(postId);
        if (post.getUser_id()==userId) {
            req.setAttribute("post", post);
            req.setAttribute("userId", userId);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("view/update.jsp");
            try {
                requestDispatcher.forward(req, resp);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                resp.sendRedirect("/facebook?action=home&id="+userId);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showFormMesseage(HttpServletRequest req, HttpServletResponse resp) {
        int userId= Integer.parseInt(req.getParameter("userId"));
        int friendId= Integer.parseInt(req.getParameter("friendId"));
        List<User> userList=userService.findAllExceptId(userId);
        List<Messeage> listMess= messageService.findByTwoId(userId,friendId);
        req.setAttribute("userId",userId);
        req.setAttribute("listMess",listMess);
        req.setAttribute("userList",userList);
        RequestDispatcher dispatcher=req.getRequestDispatcher("view/messForm.jsp");
        try {
            dispatcher.forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void likePost(HttpServletRequest req, HttpServletResponse resp) {
        int userId= Integer.parseInt(req.getParameter("userId"));
        int postId= Integer.parseInt(req.getParameter("postId"));
        Likes like =new Likes(postId,userId);
        int rowEffect =likesService.creatLike(like);
        if (rowEffect>0){
            User user=userService.findById(userId);
            int user_id=postService.findById(postId).getUser_id();
            String contentNotice="<img src=\""+user.getAvatar()+"\" width=\"50px\">" + user.getAccount() + " liked " + "post id "+postId;
            Notice notice =new Notice(user_id,contentNotice);
            noticeService.creatNotice(notice);
        }
        try {
            resp.sendRedirect("/facebook?action=home&id="+userId);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void showAllPost(HttpServletRequest req, HttpServletResponse resp) {
        int user_id= Integer.parseInt(req.getParameter("id"));
        User user=userService.findById(user_id);
        List<Post> list=postService.findAll();
        List<User> listUser=userService.findAllExceptId(user_id);
        List<Notice> listNotice=noticeService.findNoticeByUser_id(user_id);
        req.setAttribute("user",user);
        req.setAttribute("listUser",listUser);
        req.setAttribute("userId",user_id);
        req.setAttribute("list",list);
        req.setAttribute("listNotice",listNotice);
        RequestDispatcher dispatcher =req.getRequestDispatcher("view/home.jsp");
        try {
            dispatcher.forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action=req.getParameter("action");
        if(action==null)action="";
        switch (action){
            case "comment":
                comment(req,resp);
                break;
            case "messeage":
                creatMesseage(req,resp);
                break;
            case "create":
                createPost(req, resp);
                break;
            case "edit":
                updatePost(req, resp);
                break;
            case "find":
                findUser(req,resp);
                break;
        }

    }

    private void findUser(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("searchId"));
        int userId=Integer.parseInt(req.getParameter("userId"));;
        User user= userService.findById(id);
        if (user==null) {
            RequestDispatcher dispatcher =req.getRequestDispatcher("view/notFound.jsp");
            try {
                dispatcher.forward(req,resp);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                resp.sendRedirect("/facebook?action=profile&userId="+userId+"&proId="+id);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void updatePost(HttpServletRequest req, HttpServletResponse resp) {
        int postId = Integer.parseInt(req.getParameter("postId"));
        int userId=Integer.parseInt(req.getParameter("userId"));
        String content = req.getParameter("content");
        Post post=postService.findById(postId);
        post.setContent(content);
        postService.update(post);
        try {
            resp.sendRedirect("/facebook?action=home&id="+userId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        int user_id = Integer.parseInt(req.getParameter("userId"));
        String content = req.getParameter("content");
        String image=req.getParameter("image");
        Post post = new Post( image, content, user_id);
        postService.create(post);
        String redirectURL = "/facebook?action=home&id=" + user_id;
        resp.sendRedirect(redirectURL);
    }

    private void creatMesseage(HttpServletRequest req, HttpServletResponse resp) {
        int userId= Integer.parseInt(req.getParameter("userId"));
        int friendId= Integer.parseInt(req.getParameter("friendId"));
        String content=req.getParameter("messContent");
        Messeage messeage=new Messeage(userId,friendId,content);
        messageService.createMess(messeage);
        try {
            resp.sendRedirect("/facebook?action=messeage&userId="+userId+"&friendId="+friendId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void comment(HttpServletRequest req, HttpServletResponse resp) {
        int userId= Integer.parseInt(req.getParameter("userId"));
        int postId= Integer.parseInt(req.getParameter("postId"));
        String content=req.getParameter("content");
        Comment comment=new Comment(userId,postId,content);
        int rowEffect=commentService.createComment(comment);
        if (rowEffect>0){
            User user=userService.findById(userId);
            int user_id=postService.findById(postId).getUser_id();
            String contentNotice="<img src=\""+user.getAvatar()+"\" width=\"50px\">" + user.getAccount() + " commented " + "post id "+postId;
            Notice notice =new Notice(user_id,contentNotice);
            noticeService.creatNotice(notice);
        }
        try {
            resp.sendRedirect("/facebook?action=home&id="+userId);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

