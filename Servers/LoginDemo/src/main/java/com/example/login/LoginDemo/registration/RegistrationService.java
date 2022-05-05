package com.example.login.LoginDemo.registration;

import com.example.login.LoginDemo.appuser.AppUser;
import com.example.login.LoginDemo.appuser.AppUserRole;
import com.example.login.LoginDemo.appuser.AppUserService;
import com.example.login.LoginDemo.registration.token.ConfirmationToken;
import com.example.login.LoginDemo.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final ConfirmationTokenService confirmationTokenService;

    private final EmailValidator emailValidator;

    public String register(RegistrationRequest request) {

        boolean isValidEmail = emailValidator.test(request.getEmail());
        if(!isValidEmail){
            throw new IllegalStateException("email not valid");
        }

        return appUserService.signUpUser(
                new AppUser(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        AppUserRole.USER
                )
        );
    }

    @Transactional
    public String confirmToken(String token){
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() -> new IllegalStateException("token not found"));

        if(confirmationToken.getConfirmedAt() != null){
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiresAt = confirmationToken.getExpiresAt();

        LocalDateTime currentTime = LocalDateTime.now();
        if(expiresAt.isBefore(currentTime)){
            throw new IllegalStateException("token expired");
        }

        confirmationToken.setConfirmedAt(currentTime);

        appUserService.enableAppUser(confirmationToken.getAppUser().getEmail());

        return "confirmed";
    }
}
