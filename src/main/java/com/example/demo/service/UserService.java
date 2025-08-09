package com.example.demo.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Form.UserUpdateForm;
import com.example.demo.model.AppUser;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public AppUser registerNewUser(String username, String email, String password) {
        // emailで存在チェック
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("メールアドレスが既に存在します");
        }
        // usernameはユニークでなくてもよいため、存在チェックは不要
        
        AppUser user = new AppUser();
        user.setEmail(email);       // メールアドレスをログインIDとして設定
        user.setUsername(username); // 表示名として設定
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    /**
     * emailを指定してユーザーを取得
     */
    public AppUser findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }


    @Transactional
    public void updateUser(String currentEmail, UserUpdateForm form) { // 引数名をcurrentEmailに変更
        Optional<AppUser> userOptional = userRepository.findByEmail(currentEmail);

        if (userOptional.isPresent()) {
            AppUser user = userOptional.get();

            // emailが変更された場合、新しいemailが一意かチェック
            if (!user.getEmail().equals(form.getEmail())) {
                if (userRepository.findByEmail(form.getEmail()).isPresent()) {
                    throw new RuntimeException("そのメールアドレスは既に使用されています。");
                }
            }
            
            // 更新処理
            user.setEmail(form.getEmail());
            user.setUsername(form.getUsername()); // formにはusernameのデータが入っている
            userRepository.save(user);
        } else {
            throw new RuntimeException("ユーザーが見つかりませんでした。");
        }
    }
}
