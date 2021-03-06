package model;

public class Messeage {
    private int id;
    private int user_id;
    private int friend_id;
    private String content;
    private User user;

    public Messeage(int id, int user_id, int friend_id, String content) {
        this.id = id;
        this.user_id = user_id;
        this.friend_id = friend_id;
        this.content = content;
    }

    public Messeage(int user_id, int friend_id, String content) {
        this.user_id = user_id;
        this.friend_id = friend_id;
        this.content = content;
    }
    public String getAvatar(){
        return this.user.getAvatar();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(int friend_id) {
        this.friend_id = friend_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

