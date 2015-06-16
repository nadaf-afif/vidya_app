package com.sudosaints.cmavidya.model;

/**
 * Created by inni on 15/8/14.
 */
public class RegisterApiResponce {
    private int IdUserType;
    private boolean IsActive;
    private String Comments;

    public int getIdUserType() {
        return IdUserType;
    }

    public void setIdUserType(int idUserType) {
        IdUserType = idUserType;
    }

    public boolean isActive() {
        return IsActive;
    }

    public void setActive(boolean isActive) {
        IsActive = isActive;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }
}
