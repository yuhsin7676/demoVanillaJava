package com.example.demoVanillaJava.shared.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDto {
    private String login;
    private String password;
    private String name;
}
