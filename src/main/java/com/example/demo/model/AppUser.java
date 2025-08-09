package com.example.demo.model; // パッケージ名が正しいことを確認

import java.util.HashSet; // ロールを複数持たせる場合
import java.util.Set; // ロールを複数持たせる場合

import jakarta.persistence.CollectionTable; // ロールを複数持たせる場合
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection; // ロールを複数持たせる場合
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType; // ロールを複数持たせる場合
import jakarta.persistence.Enumerated; // ロールを複数持たせる場合
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn; // ロールを複数持たせる場合
// Lombokを使わない場合、Getter/Setter/コンストラクタは手動で記述
// @Data // Lombokを使っている場合、これを追加するとGetter/Setter/toStringなどが自動生成
// @NoArgsConstructor // Lombokを使っている場合、引数なしコンストラクタを自動生成
// @AllArgsConstructor // Lombokを使っている場合、全引数コンストラクタを自動生成

@Entity // このクラスがデータベースのエンティティであることを示す
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email; // ユーザー名として使用

    @Column(nullable = false)
    private String username; // 表示名など

    @Column(nullable = false)
    private String password; // ハッシュ化されたパスワード

    // ロールを複数持たせる場合
    @ElementCollection(targetClass = Role.class)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Set<Role> roles = new HashSet<>();

    public enum Role {
        USER, ADMIN // 必要に応じてロールを追加
    }

    // コンストラクタ
    public AppUser() {}

    public AppUser(String email, String name, String password, Set<Role> roles) {
        this.email = email;
        this.username = name;
        this.password = password;
        this.roles = roles;
    }

    // GetterとSetter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() { // getName() -> getUsername() に修正
        return username;
    }
    public void setUsername(String username) { // setName() -> setUsername() に修正
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }
}