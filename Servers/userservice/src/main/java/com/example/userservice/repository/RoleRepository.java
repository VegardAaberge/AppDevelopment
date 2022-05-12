package com.example.userservice.repository;


import com.example.userservice.model.AppRole;
import com.example.userservice.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<AppRole, Long> {

    AppRole findByName(String role);
}
