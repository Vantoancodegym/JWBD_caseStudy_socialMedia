package service.userService;

import model.User;
import storage.GetConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IUser{
    @Override
    public List<User> findAll() {
        List<User> list=new ArrayList<>();
        Connection connection = GetConnection.getConnetion();
        try {
            PreparedStatement preparedStatement=connection.prepareStatement("select * from user");
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                int id=resultSet.getInt("id");
                String account=resultSet.getString("account");
                String password =resultSet.getString("password");
                String email=resultSet.getString("email");
                String avatar=resultSet.getString("avatar");
                int phoneNumber=resultSet.getInt("phoneNumber");
                String address=resultSet.getString("address");
                User user=new User(id,account,password,email,avatar,phoneNumber,address);
                list.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public User findById(int id) {
        User user=null;
        Connection connection=GetConnection.getConnetion();
        try {
            PreparedStatement preparedStatement= connection.prepareStatement("select * from user where id=?");
            preparedStatement.setInt(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                String account=resultSet.getString("account");
                String password =resultSet.getString("password");
                String email=resultSet.getString("email");
                String avatar=resultSet.getString("avatar");
                int phoneNumber=resultSet.getInt("phoneNumber");
                String address=resultSet.getString("address");
                user=new User(id,account,password,email,avatar,phoneNumber,address);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return user;
    }

    @Override
    public boolean create(User user) {
        Connection connection=GetConnection.getConnetion();
        int rowEffect=0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into User(account, password, email, avatar, phoneNumber, address) value (?,?,?,?,?,?)");

            preparedStatement.setString(1, user.getAccount());
            preparedStatement.setString(2,user.getPassword());
            preparedStatement.setString(3,user.getEmail());
            preparedStatement.setString(4,user.getAvatar());
            preparedStatement.setInt(5,user.getPhoneNumber());
            preparedStatement.setString(6,user.getAddress());
            rowEffect=preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (rowEffect>0) return true;
        return false;
    }

    @Override
    public boolean update(User user) {
        return false;
    }
}
