package com.example.demo.repository; // パッケージ名が正しいことを確認

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.AppUser;

@Repository // このインターフェースがSpringのコンポーネントであることを示す
public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);
    // JpaRepositoryを継承することで、Userエンティティと主キーの型(Long)を指定するだけで、
    // 基本的なCRUD操作（保存、検索、更新、削除など）のメソッドが自動で提供されます。
    // ここに特別なメソッドを記述しない場合でも、これで十分です。
}