package com.wallet.digitalwallet.user.repository;

import com.wallet.digitalwallet.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

// ✅ JpaRepository gives us free methods:
//    save(), findById(), findAll(), deleteById() etc.
//    We don't write SQL — Spring does it for us!
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // ✅ Spring auto-generates SQL: SELECT * FROM users WHERE phone_number = ?
    Optional<User> findByPhoneNumber(String phoneNumber);

    // ✅ Spring auto-generates SQL: SELECT * FROM users WHERE email = ?
    Optional<User> findByEmail(String email);

    // ✅ Check if phone already exists (for validation)
    boolean existsByPhoneNumber(String phoneNumber);

    // ✅ Check if email already exists (for validation)
    boolean existsByEmail(String email);
}