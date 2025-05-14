package com.example.adminpanel.dto;

import java.util.List;
import java.util.Objects;

public class UserDto {
    private String userId;
    private String email;
    private String displayName;
    private String bio;
    private String profilePicture;
    private List<String> roles;

    public UserDto() {
    }

    public UserDto(String userId, String email, String displayName, String bio, String profilePicture, List<String> roles) {
        this.userId = userId;
        this.email = email;
        this.displayName = displayName;
        this.bio = bio;
        this.profilePicture = profilePicture;
        this.roles = roles;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getBio() {
        return bio;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public List<String> getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "userId='" + userId + '\'' +
                ", email='" + email + '\'' +
                ", displayName='" + displayName + '\'' +
                ", bio='" + bio + '\'' +
                ", profilePicture='" + profilePicture + '\'' +
                ", roles=" + roles +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(userId, userDto.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
