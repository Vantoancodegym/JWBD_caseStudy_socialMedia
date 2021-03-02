package model;

import java.util.List;

public class Post {
    private int id;
    private String image;
    private String content;
    private int user_id;
    private User user;
    private int likeAmount;
    private List<Comment> list;


    public Post(String image, String content, int user_id) {
        this.image = image;
        this.content = content;
        this.user_id = user_id;
    }

    public Post(int id, String image, String content, int user_id) {
        this.id=id;
        this.image = image;
        this.content = content;
        this.user_id = user_id;
    }

    public int getCommentAmount(){
        return list.size();
    }

    public String getUserAvatar(){
        return getUser().getAvatar();
    }
    public String getUserAcount(){
        return getUser().getAccount();
    }
    public String getUserEmail(){
        return getUser().getEmail();
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setLikeAmount(int likeAmount) {
        this.likeAmount = likeAmount;
    }

    public int getLikeAmount() {
        return likeAmount;
    }

    public List<Comment> getList() {
        return list;
    }

    public void setList(List<Comment> list) {
        this.list = list;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
