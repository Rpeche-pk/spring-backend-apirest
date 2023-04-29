package com.lrpa.springboot.backend.apirest.services;

import com.lrpa.springboot.backend.apirest.models.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface IClienteService {

     List<Cliente> findAll();
     Page<Cliente> limitFindAll(Pageable page);
     Cliente save(Cliente cliente);
     void delete(Long id);
     Cliente findById(Long id);

}
