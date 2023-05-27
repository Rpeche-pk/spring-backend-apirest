package com.lrpa.springboot.backend.apirest.services.impl;

import com.lrpa.springboot.backend.apirest.models.dao.IClienteDao;
import com.lrpa.springboot.backend.apirest.models.dao.IFacturaDao;
import com.lrpa.springboot.backend.apirest.models.entity.Cliente;
import com.lrpa.springboot.backend.apirest.models.entity.Factura;
import com.lrpa.springboot.backend.apirest.models.entity.Region;
import com.lrpa.springboot.backend.apirest.services.IClienteService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ClienteServiceImpl implements IClienteService {

    private final IClienteDao clienteDao;
    private final IFacturaDao facturaDao;

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {

        return (List<Cliente>) clienteDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Cliente> limitFindAll(Pageable page) {
        return clienteDao.findAll(page);
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

    @Override
    @Transactional(readOnly = true)
    public List<Region> findAllRegiones() {
        return clienteDao.findAllRegiones();
    }

    @Override
    @Transactional
    public Factura findFacturaById(Long id) {
        return facturaDao.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public Factura saveFacturaById(Factura factura) {
        return facturaDao.save(factura);
    }

    @Override
    @Transactional
    public void deleteFacturaById(Long id) {
        facturaDao.deleteById(id);
    }
}
