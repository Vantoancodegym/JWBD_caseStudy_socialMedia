package controller;

import model.User;
import service.userService.IUser;
import service.userService.UserService;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    private IUser userSevice = new UserService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        User user = new User(account,password);
        List<User> userList = userSevice.findAll();
        boolean check = false;
        int userId = 0;
        for (User p:userList) {
            if( account.equals(p.getAccount()) && password.equals(p.getPassword()) ){
                userId = p.getId();
                check = true;
                break;
            }
        }
        if(check){
            response.sendRedirect("/facebook?action=home&id="+userId);

        }else{
            request.setAttribute("msg","! account or password is not true");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("view/login.jsp");
            requestDispatcher.forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("view/login.jsp");
                requestDispatcher.forward(request, response);
    }
}
