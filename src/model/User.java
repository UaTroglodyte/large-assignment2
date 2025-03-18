package model;


public class User {
    private String username;
    private String password;

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUser(){
        return username;
    }

    public String getPassword(){
        return password;
    }
}
