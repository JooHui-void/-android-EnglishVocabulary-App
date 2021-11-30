package com.example.vocaproject;

import java.util.List;

public class UserAccount {

    private String idToken;
    private String emailId;
    private String password;
    private String name;
    private String profileImageUrl;

//    private List<WordBook> myBook;
//    private List<Integer> myInfo;

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public UserAccount(){}

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    //    public List<WordBook> getMyBook() {
//        return myBook;
//    }
//
//    public void setMyBook(List<WordBook> myBook) {
//        this.myBook = myBook;
//    }
//
//    public List<Integer> getMyInfo() {
//        return myInfo;
//    }
//
//    public void setMyInfo(List<Integer> myInfo) {
//        this.myInfo = myInfo;
//    }
}
