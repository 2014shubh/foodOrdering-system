package com.impetus.ogos.controllers;

import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.impetus.ogos.dto.CustomerDto;
import com.impetus.ogos.dto.ForgotPasswordDto;
import com.impetus.ogos.dto.PasswordToken;
import com.impetus.ogos.models.User;
import com.impetus.ogos.payload.responses.MessageResponse;
import com.impetus.ogos.services.ForgotPasswordService;
import net.bytebuddy.utility.RandomString;

@RestController
@RequestMapping("/auth/pass")
public class ForgotPasswordController {
	
	@Autowired
	ForgotPasswordService forgotPassword;
	
	@Autowired
	MailSender mailSender;
	
	Logger logger = LoggerFactory.getLogger(ForgotPasswordService.class);

	/**
	 * @param user
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws MessagingException
	 */
	@PostMapping("/forgot-password")
    public ResponseEntity<MessageResponse> processForgotPassword(@Valid @RequestBody ForgotPasswordDto user) throws UnsupportedEncodingException, MessagingException {
		logger.info("Inside processForgotPassword method of ForgotPasswordController");
        String token = RandomString.make(30);
        String email =user.getEmail();
        try {
            forgotPassword.updateResetPasswordToken(token, email);
            return sendEmail(email, token);
        } catch (UsernameNotFoundException ex) {
        	return ResponseEntity.badRequest().body(new MessageResponse("User not found with this email"));
        }
    }
 

    @PostMapping("/reset-password")
    public ResponseEntity<MessageResponse> processResetPassword(@Valid @RequestBody PasswordToken request) {
    	 logger.info("Inside processResetPassword method of ForgotPasswordController");
        String token = request.getToken();
        String password = request.getPassword();
        User user = forgotPassword.getByResetPasswordToken(token).orElse(null);
        if (user == null) {
        	return ResponseEntity.badRequest().body(new MessageResponse("Invalid Token"));
        } else {
            forgotPassword.updatePassword(user, password);
            return ResponseEntity.ok(new MessageResponse("Password has been updated"));
        }

    }

    public ResponseEntity<MessageResponse> sendEmail(String recipientEmail, String token) {
    	
        String subject = "Here's the link to reset your password";
        SimpleMailMessage simplemail = new SimpleMailMessage();
        simplemail.setFrom("ogosmanagement@gmail.com");
        simplemail.setTo(recipientEmail);
        simplemail.setText("Token : " + token);
        simplemail.setSubject(subject);        
        mailSender.send(simplemail);
        return ResponseEntity.ok(new MessageResponse("A token has been sent to your registered email"));
    }
}
