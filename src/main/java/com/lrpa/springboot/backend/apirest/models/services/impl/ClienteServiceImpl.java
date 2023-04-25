package com.lrpa.springboot.backend.apirest.models.services.impl;

import com.lrpa.springboot.backend.apirest.models.dao.IClienteDao;
import com.lrpa.springboot.backend.apirest.models.entity.Cliente;
import com.lrpa.springboot.backend.apirest.models.services.IClienteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ClienteServiceImpl implements IClienteService {

    private final IClienteDao clienteDao;

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        return (List<Cliente>) clienteDao.findAll();
    }

    @Override
    @Transactional
    public Cliente save(Cliente cliente) {
        return clienteDao.save(cliente);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        clienteDao.deleteById(id);
    }

    @Transactional
    @Override
    public Cliente findById(Long id) {
        return clienteDao.findById(id).orElse(null);
    }
}
