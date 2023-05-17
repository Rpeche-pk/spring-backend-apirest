package com.lrpa.springboot.backend.apirest.services;

import com.lrpa.springboot.backend.apirest.models.entity.Usuario;

public interface IUsuarioService {

    Usuario findByUsername(String username);
}
