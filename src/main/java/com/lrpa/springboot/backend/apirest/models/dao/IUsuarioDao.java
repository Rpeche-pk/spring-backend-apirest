package com.lrpa.springboot.backend.apirest.models.dao;

import com.lrpa.springboot.backend.apirest.models.entity.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IUsuarioDao extends CrudRepository<Usuario,Long> {

    Usuario findByUsername(String username);
}
