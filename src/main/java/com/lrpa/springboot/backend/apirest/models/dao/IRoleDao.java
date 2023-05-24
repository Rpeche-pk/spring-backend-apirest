package com.lrpa.springboot.backend.apirest.models.dao;

import com.lrpa.springboot.backend.apirest.models.entity.Role;
import org.springframework.data.repository.CrudRepository;

public interface IRoleDao extends CrudRepository<Role,Long> {
}
