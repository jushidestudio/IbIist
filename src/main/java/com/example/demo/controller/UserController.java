package com.example.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.Form.UserUpdateForm;
import com.example.demo.model.AppUser;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * マイページ表示
     */
    @GetMapping("/mypage")
    public String showMyPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        AppUser user = userService.findByEmail(userDetails.getUsername());

        if (user == null) {
            model.addAttribute("errorMessage", "ユーザー情報が見つかりませんでした。再度ログインしてください。");
            return "redirect:/login"; // 例：ログインページにリダイレクト
        }

        UserUpdateForm form = new UserUpdateForm();
        form.setUsername(user.getUsername()); // 修正: user.getName() から user.getUsername() へ
        form.setEmail(user.getEmail());

        model.addAttribute("user", user);
        model.addAttribute("userUpdateForm", form);
        return "user/mypage";
    }

    /**
     * ユーザー情報更新
     */
    @PostMapping("/update")
    public String updateUser(@AuthenticationPrincipal UserDetails userDetails,
                             @Validated @ModelAttribute UserUpdateForm userUpdateForm,
                             BindingResult result,
                             RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            // バリデーションエラーがある場合はフォームに戻る
            // userオブジェクトも再度取得してmodelに追加する必要がある
            return "user/mypage";
        }

        try {
            userService.updateUser(userDetails.getUsername(), userUpdateForm);
            redirectAttributes.addFlashAttribute("successMessage", "ユーザー情報を更新しました。");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "更新に失敗しました。");
        }
        
        return "redirect:/user/mypage";
    }
}