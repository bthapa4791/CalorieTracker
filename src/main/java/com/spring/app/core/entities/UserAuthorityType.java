package com.spring.app.core.entities;

public enum UserAuthorityType {
    USER("USER"),
    DBA("DBA"),
    ADMIN("ADMIN");
     
    String userAuthorityType;
     
    private UserAuthorityType(String userAuthorityType){
        this.userAuthorityType = userAuthorityType;
    }
     
    public String getUserProfileType(){
        return userAuthorityType;
    }
     
}