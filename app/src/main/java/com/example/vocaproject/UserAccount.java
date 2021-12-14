package com.example.vocaproject;

import java.util.List;

public class UserAccount {

    private String idToken;
    private String emailId;
    private String password;
    private String name;
    private String profileImageUrl;
    private int myCorrectNum;

    private List<Integer> incorrectWord;
    private List<Integer> userView;

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

    public List<Integer> getIncorrectWord() {
        return incorrectWord;
    }

    public void setIncorrectWord(List<Integer> incorrectWord) {
        this.incorrectWord = incorrectWord;
    }

    public List<Integer> getUserView() {
        return userView;
    }

    public void setUserView(List<Integer> userView) {
        this.userView = userView;
    }

    public int getMyCorrectNum() {
        return myCorrectNum;
    }

    public void setMyCorrectNum(int myCorrectNum) {
        this.myCorrectNum = myCorrectNum;
    }

}
