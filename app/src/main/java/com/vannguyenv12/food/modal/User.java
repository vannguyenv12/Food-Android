//package com.vannguyenv12.food.modal;
//
//public class User {
//    private int id;
//    private String email;
//    private String password;
//    private String firstName;
//    private String lastName;
//    private String role;
//
//    public User() {
//    }
//
//    public User(int id, String email, String password, String firstName, String lastName, String role) {
//        this.id = id;
//        this.email = email;
//        this.password = password;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.role = role;
//    }
//
////    public User(String email, String password, String firstName, String lastName, String role) {
////        this.email = email;
////        this.password = password;
////        this.firstName = firstName;
////        this.lastName = lastName;
////        this.role = role;
////    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//    public String getRole() {
//        return role;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }
//}


/////////////////////////////////
package com.vannguyenv12.food.modal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    private int id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String role;

    @JsonProperty("created_at")
    private String createdAt;

    public User() {
    }

    public User(int id, String email, String password, String firstName, String lastName, String role, String createdAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
