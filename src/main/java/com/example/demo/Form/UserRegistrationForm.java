package com.example.demo.Form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRegistrationForm {

    @NotBlank(message = "ユーザー名は必須です")
    @Size(min = 3, max = 20, message = "ユーザー名は3文字以上20文字以内で入力してください")
    private String username;

    @NotBlank(message = "メールアドレスは必須です")
    @Email(message = "有効なメールアドレスを入力してください")
    private String email;

    @NotBlank(message = "パスワードは必須です")
    @Size(min = 8, message = "パスワードは8文字以上で入力してください")
    private String password;

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}