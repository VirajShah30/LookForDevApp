package com.example.lookfordev;

public class UserHelperClass {
    String Name,Email,Role;

    public UserHelperClass() {
    }

    public UserHelperClass(String name, String email, String role) {
        this.Name = name;
        this.Email = email;
        this.Role = role;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public void setRole(String role) {
        this.Role = role;
    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }

    public String getRole() {
        return Role;
    }
}
