package me.romo.sgstock.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUserRequest {
    private String publicId;
    private String password;


}
