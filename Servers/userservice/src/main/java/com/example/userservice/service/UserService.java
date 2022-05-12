package com.example.userservice.service;

import com.example.userservice.model.AppRole;
import com.example.userservice.model.AppUser;

import java.util.List;

public interface UserService {
    AppUser saveUser(AppUser user);
    AppRole saveRole(AppRole role);
    String addRoleToUser(String username, String roleName);
    AppUser getUser(String username);
    List<AppUser> getUsers();
}
