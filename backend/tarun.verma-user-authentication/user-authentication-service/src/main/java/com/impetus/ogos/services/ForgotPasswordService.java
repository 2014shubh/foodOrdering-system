package com.impetus.ogos.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.impetus.ogos.models.User;
import com.impetus.ogos.repository.UserRepository;

@Service
public class ForgotPasswordService {
    @Autowired
    private UserRepository UserRepo;

 

    public void updateResetPasswordToken(String token, String email) throws UsernameNotFoundException {
        User user = UserRepo.findByEmail(email).orElse(null);
        if (user != null) {
            user.setResetPasswordToken(token);
            UserRepo.save(user);
        } else {
            throw new UsernameNotFoundException("Could not find any User with the email " + email);
        }
    }

 

    public Optional<User> getByResetPasswordToken(String token) {
        return UserRepo.findByResetPasswordToken(token);
    }
 

    public void updatePassword(User User, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        User.setPassword(encodedPassword);
        User.setResetPasswordToken(null);
        UserRepo.save(User);
    }
}
