package com.example.demo.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.AppUser;
import com.example.demo.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // ...
        AppUser appUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("ユーザーが見つかりません: " + email));

        return org.springframework.security.core.userdetails.User.builder()
                .username(appUser.getEmail()) // ログインIDとして使用する email を設定
                .password(appUser.getPassword())
                .roles("USER") // または適切なロールを設定
                .build();
    }
}
