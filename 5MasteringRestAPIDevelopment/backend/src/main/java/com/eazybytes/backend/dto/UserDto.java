package com.eazybytes.backend.dto;

public record UserDto(
        String name,
        String email,
        String gender) {

    @Override
    public String toString() {
        return "UserDto{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
