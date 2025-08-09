package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.Form.UserRegistrationForm;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userRegistrationForm", new UserRegistrationForm());
        return "register"; // register.htmlを表示
    }

    @PostMapping("/register")
    public String registerUserAccount(@ModelAttribute("userRegistrationForm") @Valid UserRegistrationForm form,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        try {
            userService.registerNewUser(form.getUsername(), form.getEmail(), form.getPassword());
        } catch (RuntimeException e) {
            bindingResult.rejectValue("email", "user.email", e.getMessage()); // 例外メッセージをエラーとして追加
            bindingResult.rejectValue("username", "user.username", e.getMessage());
            return "register";
        }

        redirectAttributes.addFlashAttribute("message", "ユーザー登録が完了しました！");
        return "redirect:/login"; // 登録成功後、ログインページへリダイレクト
    }
}
