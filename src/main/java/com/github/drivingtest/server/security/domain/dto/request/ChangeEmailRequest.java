package com.github.drivingtest.server.security.domain.dto.request;

import javax.validation.constraints.NotBlank;

public class ChangeEmailRequest {

    @NotBlank(message = "Password cannot be blank")
    String password;

    @NotBlank(message = "New email cannot be blank")
    String newEmail;

    public ChangeEmailRequest() {
    }

    public ChangeEmailRequest(String password, String newEmail) {
        this.password = password;
        this.newEmail = newEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }
}
