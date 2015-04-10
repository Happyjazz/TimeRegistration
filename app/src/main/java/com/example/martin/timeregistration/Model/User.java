package com.example.martin.timeregistration.Model;

import java.io.Serializable;

/**
 * Created by Martin on 09-03-2015.
 */
public class User implements Serializable {
    private static final long serialVersionUID = 8753820806403933874L;
    private final int id;
    private final String username;
    private String password;
    private final String firstname;
    private final String lastname;

    public User(final int id, final String username, final String password,
                final String firstname, final String lastname) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public boolean setPassword(String newPassword) {
        try {
            this.password = newPassword;
            return true;
        } catch (Exception e) {
           return false;
        }
    }

    @Override
    public String toString() {
        return id + " : " + username + " : " + firstname + " : " + lastname;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

}

