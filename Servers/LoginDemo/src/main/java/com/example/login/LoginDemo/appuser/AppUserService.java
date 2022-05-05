package com.example.login.LoginDemo.appuser;

import com.example.login.LoginDemo.registration.token.ConfirmationToken;
import com.example.login.LoginDemo.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";
    private final AppUserRepository appUserRepository;
    private final ConfirmationTokenService confirmationTokenService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return appUserRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email))
                );
    }

    public String signUpUser(AppUser appUser) {
        boolean userExist = appUserRepository
                .findByEmail(appUser.getEmail())
                .isPresent();

        if(userExist){
            if(appUser.isEnabled()){
                throw new IllegalStateException("email already taken");
            }

            String email = appUser.getEmail();
            AppUser currentAppUser = appUserRepository.findByEmail(email)
                    .orElseThrow(() ->
                            new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email))
                    );

            if(!currentAppUser.isRequestEqual(appUser)){
                throw new IllegalStateException("the user is not the same");
            }

            appUser = currentAppUser;
        }

        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);

        appUserRepository.save(appUser);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }

    public void enableAppUser(String email) {
        AppUser appUser = appUserRepository.findByEmail(email)
                .orElseThrow(() ->new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));

        appUser.setEnabled(true);

        appUserRepository.save(appUser);
    }
}
