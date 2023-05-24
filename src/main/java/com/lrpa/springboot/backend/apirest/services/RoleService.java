package com.lrpa.springboot.backend.apirest.services;

import com.lrpa.springboot.backend.apirest.models.entity.Role;

import java.util.Optional;

public interface RoleService {

    Optional<Role> findById(Long id);
}
