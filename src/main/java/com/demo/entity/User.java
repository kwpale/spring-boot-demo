package com.demo.entity;

import com.demo.base.AbstractEntity;
import com.demo.base.UserType;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Entity
public class User extends AbstractEntity {
    /**
     * username = email
     */
    @NotEmpty
    @Column(nullable = false, unique = true)
    private String username;

    @Column(name="first_name")
    public String firstName;

    @Column(name="last_name")
    public String lastName;

    @NotEmpty
    public String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private UserType userType;

    private String address;

    @NotEmpty
    @Column(unique = true)
    private String email;

    @Column
    private boolean active = true;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;

        User user = (User) o;

        if (getUsername() != null ? !getUsername().equals(user.getUsername()) : user.getUsername() != null)
            return false;
        return getUserType() == user.getUserType();
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getUsername() != null ? getUsername().hashCode() : 0);
        result = 31 * result + (getUserType() != null ? getUserType().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", userType=" + userType +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", active=" + active +
                ", id=" + id +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                ", version=" + version +
                '}';
    }
}
