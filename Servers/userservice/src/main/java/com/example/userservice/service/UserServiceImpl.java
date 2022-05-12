package com.example.userservice.service;

import com.example.userservice.model.AppRole;
import com.example.userservice.model.AppUser;
import com.example.userservice.repository.RoleRepository;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public AppUser saveUser(AppUser user) {
        log.info("Saving new user {} to the database", user.getName());
        return userRepository.save(user);
    }

    @Override
    public AppRole saveRole(AppRole role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public String addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);
        AppUser user = userRepository.findByUsername(username);
        AppRole role = roleRepository.findByName(roleName);
        user.getRoles().add(role);

        return String.format("Adding role %s to user %s", roleName, username);
    }

    @Override
    public AppUser getUser(String username) {
        log.info("Fetching user {}", username);
        return userRepository.findByUsername(username);
    }

    @Override
    public List<AppUser> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }
}
