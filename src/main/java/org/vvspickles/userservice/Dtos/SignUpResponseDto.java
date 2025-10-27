package org.vvspickles.userservice.Dtos;

public class SignUpResponseDto {
    private String name;
    private String email;

    public SignUpResponseDto(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }

}
