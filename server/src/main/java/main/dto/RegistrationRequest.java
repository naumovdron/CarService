package main.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RegistrationRequest implements Serializable {
    private String userName;
    private String password;
    private String passwordConfirm;

    public RegistrationRequest(String userName, String password, String passwordConfirm) {
        this.userName = userName;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }
}
