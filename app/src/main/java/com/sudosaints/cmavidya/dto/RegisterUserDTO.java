package com.sudosaints.cmavidya.dto;

import com.sudosaints.cmavidya.model.User;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by inni on 28/8/14.
 */
public class RegisterUserDTO implements Serializable {
    @JsonProperty("UserName")
    private String userName;
    @JsonProperty("FirstName")
    private String firstName;
    @JsonProperty("LastName")
    private String lastName;
    @JsonProperty("Password")
    private String password;
    @JsonProperty("IdCourseMaster")
    private int idCourseMaster;

    public RegisterUserDTO(User user) {
        userName = user.getUsername();
        idCourseMaster = user.getCourseId();
        firstName = user.getFirstname();
        lastName = user.getLastname();
        password = user.getPassword();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getIdCourseMaster() {
        return idCourseMaster;
    }

    public void setIdCourseMaster(int idCourseMaster) {
        this.idCourseMaster = idCourseMaster;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
